package org.csc.wlt.service.impl;

import com.alibaba.fastjson.JSON;
import com.zcs.user.api.result.ResponseInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.common.UrlConstants;
import org.csc.wlt.model.AppReqHeader;

import java.util.HashMap;
import java.util.Map;
@NActorProvider
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "userExistService")
@Slf4j
@Data
public class UserExistServiceImpl extends AbTemplateService<Wallet.BaseData,String,User.UserExistReq.Builder> implements ActorService {

    /**
     * 获得build
     * @return
     */
    protected User.UserExistReq.Builder getBuild(){
        return User.UserExistReq.newBuilder();
    }
    /**
     * 组装参数
     * @param builder
     * @return
     */
    protected Map<String,Object> param(User.UserExistReq.Builder builder){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userName",builder.getUserName());
        return paramMap;
    }
    /**
     * 获得请求的url
     * @return
     */
    protected String getReqUrl(){
        return UrlConstants.USER_EXIST_URL;
    }

    @Override
    protected boolean definedCheck(User.UserExistReq.Builder builder,
                                   AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret) {
        return true;
    }
    /**
     * 对结果进行处理
     * @param responseInfo
     * @return
     */
    protected String dealResult(ResponseInfo responseInfo){
        Map<String,String> resultMap = JSON.parseObject(JSON.toJSONString(responseInfo.getData()),Map.class);
        return resultMap.get("flag");
    }
}
