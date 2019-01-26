package org.csc.wlt.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.GeneratedMessageV3.Builder;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import com.zcs.user.api.result.ResponseInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.beans.FramePacket;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.common.Constants;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscWltParameter;
import org.csc.wlt.entity.CscWltParameterExample;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.model.UserCenterModel;
import org.csc.wlt.utils.ResultUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Data
public abstract class AbTemplateService<A extends Message,T,D extends Builder> extends CommonCheckService<A,D> {

    @ActorRequire(name = "daos", scope = "global")
    Daos daos;

    @ActorRequire(name = "resultUtil", scope = "global")
    ResultUtil resultUtil;

    RestTemplate restTemplate = new RestTemplate();

    /**
     * 集中处理业务
     * @param data
     * @param packet
     * @param ret
     * @return
     * @throws JsonFormat.ParseException
     */
    public T execute(Wallet.BaseData data, FramePacket packet, ResultOuterClass.Result.Builder ret) throws JsonFormat.ParseException{
        D builder = getBuild();
        //校验参数
        if(!check(packet,data,ret,builder)){
            return null;
        }
        //准备body
        Map<String,Object> paramMap = param(builder);
        //设置头
        HttpHeaders header = settHeader(packet,packet.getHttpServerletRequest().getHeader(Constants.GRANT_TOKEN));
        //发送请求
        ResponseInfo responseInfo = send(paramMap,header, getReqUrl());
        String code="";
        if(responseInfo == null || !(code = responseInfo.getRetCode()).equals(Constants.SUC_CODE)){
            ret.mergeFrom(resultUtil.returnByOthCode(Constants.USER_CENTER_CHAN,code));
            return null;
        }
        ret.setRplCode(ReturnCodeEnum.DONE.getCode());
        return dealResult(responseInfo);
    }

    /**
     * 组装参数
     * @param builder
     * @return
     */
    protected abstract Map<String,Object> param(D builder);
    /**
     * 获得请求的url
     * @return
     */
    protected abstract String getReqUrl();

    /**
     * 对结果进行处理
     * @param responseInfo
     * @return
     */
    protected abstract T dealResult(ResponseInfo responseInfo);

    /**
     * 获得用户中心参数
     * @return
     */
    protected UserCenterModel getUserCenterModel(FramePacket packet){
        CscWltParameterExample example = new CscWltParameterExample();
        example.createCriteria().andParamCodeEqualTo(Constants.USER_SERVICE_ID);
        CscWltParameter serviceIdParameter = (CscWltParameter) daos.wltParameterDao.selectOneByExample(example);
        if(serviceIdParameter == null){
            throw new RuntimeException("user_service_id is not config");
        }
        example = new CscWltParameterExample();
        example.createCriteria().andParamCodeEqualTo(Constants.USER_ACCESS_TOKEN);
        CscWltParameter accessTokenParameter = (CscWltParameter) daos.wltParameterDao.selectOneByExample(example);
        if(accessTokenParameter == null){
            throw new RuntimeException("user_accessToken is not config");
        }
        return UserCenterModel.builder().serviceId(serviceIdParameter.getParamValue())
                .accessToken(accessTokenParameter.getParamValue()).appId(commonHelper.getHeader(packet).getAppId()).build();
    }
    /**
     * 设置头信息
     * @param grantToken
     * @param packet
     */
    protected HttpHeaders settHeader(FramePacket packet,String grantToken){
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        header.add(Constants.X_AUTHORIZATION,JSON.toJSONString(getUserCenterModel(packet)));
        header.add(Constants.X_GRANT_TOKEN,grantToken);
        return header;
    }

    /***
     * 请求用户中心
     * @param paramMap
     * @param header
     * @param url
     * @return
     */
    protected ResponseInfo send(Map<String,Object>paramMap,HttpHeaders header,String url){
        CscWltParameterExample example = new CscWltParameterExample();
        example.createCriteria().andParamCodeEqualTo(Constants.USER_CENTER_URL);
        CscWltParameter cscWltParameter = (CscWltParameter) daos.getWltParameterDao().selectOneByExample(example);
        if(cscWltParameter == null){
            throw new RuntimeException("user center url not config");
        }
        HttpEntity<Map> entity = new HttpEntity<>(paramMap, header);
        try {
            url = cscWltParameter.getParamValue()+url;
            log.info("request userCenter url:{} param：{}",url,JSON.toJSONString(entity));
            long time1 = System.currentTimeMillis();
            ResponseEntity<ResponseInfo> exchange = restTemplate.exchange(url, HttpMethod.POST,entity, ResponseInfo.class);
            log.info("request user center time is :{} ms",(System.currentTimeMillis()-time1));
            log.info("url:{} responseInfo :{}",url,JSON.toJSONString(exchange));
            return exchange.getBody();
        }catch (Exception e){
            log.error("request userCenter url：{} param：{}",url,JSON.toJSONString(entity),e);
            return null;
        }
    }
}
