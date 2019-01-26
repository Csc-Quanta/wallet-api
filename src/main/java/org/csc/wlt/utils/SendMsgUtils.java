package org.csc.wlt.utils;

import com.alibaba.fastjson.JSON;
import com.zcs.message.bean.BaseResponse;
import com.zcs.message.bean.SmsMsgRequest;
import com.zcs.message.bean.ValidationReq;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.backend.redis.RedisTools;
import org.csc.wlt.common.CaCheConstants;
import org.csc.wlt.common.Constants;
import org.csc.wlt.common.UrlConstants;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscWltParameter;
import org.csc.wlt.entity.CscWltParameterExample;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/***
 * 调用appCommon系统相关接口
 *
 * @author fjy
 * @date 2017/06/14
 */

@NActorProvider
@Slf4j
@Data
@Instantiate(name = "sendMsgUtils")
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
public class SendMsgUtils implements ActorService{

	@ActorRequire(name = "daos", scope = "global")
	Daos daos;
	private RestTemplate restTemplate = new RestTemplate();
	@ActorRequire(name = "redisTools", scope = "global")
	RedisTools redisTools;
	@ActorRequire(name = "resultUtil", scope = "global")
	ResultUtil resultUtil;
	/*
	 * 判断短信校验结果凭证有效性
	 * @param smsPassToken
	 * @param mobile
	 * @return
	 */
	public boolean checkSmsPassStatus(String tokenKey,String value){
		tokenKey = CaCheConstants.CHECK_SMS_PASS_PRX+tokenKey;
		String redisValue = redisTools.get(tokenKey);
		if(redisValue!=null && redisValue.equals(value)){
			redisTools.delete(tokenKey);
			return true;
		}
		return false;
	}

	/**
	 * 将数据放入缓存
	 * @param tokenKey
	 * @param value
	 */
	public void put(String tokenKey,String value,int timeoutSec){
		tokenKey = CaCheConstants.CHECK_SMS_PASS_PRX+tokenKey;
		redisTools.set(tokenKey,value,timeoutSec, TimeUnit.SECONDS);
	}

	/**
	 * 调取短信服务发送验证码
	 * @param smsMsgRequest
	 * @return
	 */

	public BaseResponse sendMag(SmsMsgRequest smsMsgRequest){
		CscWltParameter parameter = getParamValue();
		BaseResponse baseResponse = null;
		try{
			String url = parameter.getParamValue()+ UrlConstants.SEND_MSG;
			log.info("call message system，send sms，url:{} in param{}",url,JSON.toJSONString(smsMsgRequest));
			String result = restTemplate.postForObject(url,smsMsgRequest, String.class);
			baseResponse = JSON.parseObject(result,BaseResponse.class);
			log.info("call message system send sms ,out param:{}",JSON.toJSONString(baseResponse));
		}catch (Exception e){
			log.error("call message system send sms  error：{}",e);
			throw new ProcessException("-1",String.valueOf(ReturnCodeEnum.EXCEPTION.getMsg()));
		}
		return baseResponse;
	}

	/**
	 * 调取短信服务校验验证码
	 * @param request
	 * @return
	 */

	public BaseResponse validCode(ValidationReq request){
		CscWltParameter parameter = getParamValue();
		BaseResponse baseResponse = null;
		try{
			String url = parameter.getParamValue()+UrlConstants.VALID_CODE;
			log.info("call message system，check smsCode，url :{} in param{}",url,JSON.toJSONString(request));
			String result = restTemplate.postForObject(url,request, String.class);
			baseResponse = JSON.parseObject(result,BaseResponse.class);
			log.info("call message system，check smsCode，out param{}",JSON.toJSONString(baseResponse));
		}catch (Exception e){
			log.error("call message system，check smsCode， error：{}",e);
			throw new ProcessException(ReturnCodeEnum.ERROR.getCode(),ReturnCodeEnum.EXCEPTION.getMsg());
		}
		return baseResponse;
	}

	/**
	 * 获取短信服务的url
	 * @return
	 */
	private CscWltParameter getParamValue(){
		CscWltParameterExample example = new CscWltParameterExample();
		example.createCriteria().andParamCodeEqualTo(Constants.MSG_SERVER_SEND_URL);
		CscWltParameter parameter = (CscWltParameter) daos.getWltParameterDao().selectOneByExample(example);
		if(parameter == null){
			throw new ProcessException(ReturnCodeEnum.ERROR.getCode(),"send msg server not config");
		}
		return parameter;
	}
}
