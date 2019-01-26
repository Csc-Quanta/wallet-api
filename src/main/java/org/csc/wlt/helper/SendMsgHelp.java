package org.csc.wlt.helper;

import com.googlecode.protobuf.format.JsonFormat;
import com.zcs.message.bean.BaseResponse;
import com.zcs.message.bean.SmsMsgRequest;
import com.zcs.message.bean.ValidationReq;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.beans.FramePacket;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.backend.redis.RedisTools;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;
import org.csc.wlt.common.CaCheConstants;
import org.csc.wlt.common.Constants;
import org.csc.wlt.common.ErrorCodeConstants;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscWltAppParam;
import org.csc.wlt.entity.CscWltAppParamModel;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.model.AppReqHeader;
import org.csc.wlt.utils.ProcessException;
import org.csc.wlt.utils.ResultUtil;
import org.csc.wlt.utils.SendMsgUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@NActorProvider
@Slf4j
@Data
@Instantiate(name = "sendMsgHelp")
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
public class SendMsgHelp implements ActorService {
    @ActorRequire(name = "daos", scope = "global")
    Daos daos;

    @ActorRequire(name = "sendMsgUtils", scope = "global")
    SendMsgUtils sendMsgUtils;

    @ActorRequire(name = "commHelper", scope = "global")
    CommonHelper commonHelper;

    @ActorRequire(name = "redisTools", scope = "global")
    RedisTools redisTools;

    @ActorRequire(name = "resultUtil", scope = "global")
    ResultUtil resultUtil;
    /**
     * 根据手机号码和类型发送短信
     * @param req
     * @param packet
     */
    public void sendMsg(User.SendMsgReq.Builder req, FramePacket packet, ResultOuterClass.Result.Builder ret){
        //准备头
        AppReqHeader appReqHeader = commonHelper.getHeader(packet);
        CscWltAppParamModel model = new CscWltAppParamModel();
        model.setGroupId(Constants.MSG_GROUP_ID);
        model.setAppId(appReqHeader.getAppId());
        model.setPropertyId(req.getType());
        CscWltAppParam cscWltAppParam = (CscWltAppParam) daos.wltAppParamDao.selectOneByExample(model);
        if(cscWltAppParam == null){
            ret.setRplCode(ReturnCodeEnum.ERROR.getCode());
            ret.setRplMsg("type is not exist");
            return;
        }
        String userName = req.getUserName();
        String appId = appReqHeader.getAppId();
        String templateId = cscWltAppParam.getPropertyName();
        String cacheKey = CaCheConstants.SEND_MSG_PRX+appId+templateId+userName;
        String value = redisTools.get(cacheKey);
        //到缓存中进行校验
        if(value!=null){
            ret.mergeFrom(resultUtil.returnResult(ErrorCodeConstants.SEND_AFTER_60_SEC));
            return;
        }
        SmsMsgRequest smsMsgRequest = new SmsMsgRequest();
        smsMsgRequest.setAppid(appId);
        smsMsgRequest.setPhone(userName);
        smsMsgRequest.setTemplateId(templateId);
        BaseResponse baseResponse;
        try{
            baseResponse = sendMsgUtils.sendMag(smsMsgRequest);
        }catch (ProcessException e){
            ret.mergeFrom(resultUtil.returnResult(ReturnCodeEnum.ERROR.getCode()));
            return;
        }
        if(!baseResponse.head.retCode.equals(Constants.SUC_CODE)){
            ret.mergeFrom(resultUtil.returnByOthCode(Constants.MESSAGE_SERVER_CHAN,baseResponse.head.retCode));
            return;
        }
        ret.setRplCode(ReturnCodeEnum.DONE.getCode());
        //放入缓存
        redisTools.set(cacheKey,userName,60, TimeUnit.SECONDS);
    }

    /**
     * 校验短信验证码
     * @param req
     * @param packet
     * @param ret
     * @throws JsonFormat.ParseException
     */
    public String checkSmsCode(User.VerifySmsCodeReq.Builder req, FramePacket packet, ResultOuterClass.Result.Builder ret){
        //准备参数
        ValidationReq validationReq = new ValidationReq();
        validationReq.setAppid(commonHelper.getHeader(packet).getAppId());
        validationReq.setPhone(req.getUserName());
        validationReq.setValidCode(req.getVerifyCode());
        BaseResponse baseResponse;
        try {
            baseResponse = sendMsgUtils.validCode(validationReq);
        }catch (ProcessException e){
            ret.mergeFrom(resultUtil.returnResult(ReturnCodeEnum.ERROR.getCode()));
            return null;
        }
        if(!baseResponse.head.retCode.equals(Constants.SUC_CODE)){
            ret.mergeFrom(resultUtil.returnByOthCode(Constants.MESSAGE_SERVER_CHAN,baseResponse.head.retCode));
            return null;
        }
        //准备放缓存
        String smsPassToken = UUID.randomUUID().toString().replace("-","");
        sendMsgUtils.put(smsPassToken,req.getUserName()+validationReq.getAppid(),60);
        ret.setRplCode(ReturnCodeEnum.DONE.getCode());
        return smsPassToken;
    }
}
