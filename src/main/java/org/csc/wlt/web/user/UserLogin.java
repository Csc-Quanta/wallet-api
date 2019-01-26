package org.csc.wlt.web.user;

import com.alibaba.fastjson.JSON;
import com.zcs.user.api.result.ResponseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.async.CompleteHandler;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.common.UrlConstants;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.model.AppReqHeader;
import org.csc.wlt.service.impl.AbTemplateService;

import java.util.HashMap;
import java.util.Map;

@NActorProvider
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class UserLogin extends AbTemplateService<Wallet.BaseData,Map, User.UserLoginReq.Builder> {
    @Override
    public String[] getCmds() {
        return new String[]{User.PUSERCommand.USL.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }

    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        ResultOuterClass.Result.Builder ret = ResultOuterClass.Result.newBuilder();
        User.UserLoginRes.Builder builder = User.UserLoginRes.newBuilder();
        try {
            Map<String,String> resultMap = execute(pb,pack,ret);
            if(resultMap == null ){
                log.info("{} ret.toString:{}",this.getClass().getSimpleName(),ret.build().toString());
                handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
                return;
            }
            builder.setCUserId(resultMap.get("userId"));
            builder.setRplCode(ReturnCodeEnum.DONE.getCode());
            builder.setGrantToken(resultMap.get("grantToken"));
        } catch (Exception e) {
            log.error("user userExist fail param :{} :{}",pb.toString(),e);
            builder.setRplCode(ReturnCodeEnum.ERROR.getCode());
            builder.setRplMsg(ReturnCodeEnum.ERROR.getMsg());
        }
        log.info("{} builder.toString:{}",this.getClass().getSimpleName(),builder.build().toString());
        handler.onFinished(PacketHelper.toPBReturn(pack, builder.build()));
    }
    /**
     * 获得build
     * @return
     */
    protected User.UserLoginReq.Builder getBuild(){
        return User.UserLoginReq.newBuilder();
    }

    /**
     * 组装参数
     * @param builder
     * @return
     */
    protected Map<String,Object> param(User.UserLoginReq.Builder builder){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("pwd",builder.getPwd());
        paramMap.put("userName",builder.getUserName());
        return paramMap;
    }

    /**
     * 获得请求的url
     * @return
     */
    protected String getReqUrl(){
        return UrlConstants.USER_LOGIN;
    }

    @Override
    protected boolean definedCheck(User.UserLoginReq.Builder builder,
                                   AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret) {
        return true;
    }

    /**
     * 对结果进行处理
     * @param responseInfo
     * @return
     */
    protected Map dealResult(ResponseInfo responseInfo){
        Map<String,String> resultMap = JSON.parseObject(JSON.toJSONString(responseInfo.getData()),Map.class);
        userCacheUtil.eval(resultMap.get("userId"),resultMap.get("grantToken"));
        return resultMap;
    }
}
