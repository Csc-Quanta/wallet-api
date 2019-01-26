package org.csc.wlt.web.user;

import com.googlecode.protobuf.format.JsonFormat;
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
public class UpdateLoginPassword extends AbTemplateService<Wallet.BaseData,Void, User.UpdateLoginPasswordReq.Builder> {
    @Override
    public String[] getCmds() {
        return new String[]{User.PUSERCommand.ULP.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }

    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        ResultOuterClass.Result.Builder ret = ResultOuterClass.Result.newBuilder();
        try {
            execute(pb,pack,ret);
        } catch (Exception e) {
            log.error("user updateLoginPassword fail param :{} :{}",pb.toString(),e);
            ret.setRplMsg(ReturnCodeEnum.ERROR.getMsg());
            ret.setRplCode(ReturnCodeEnum.ERROR.getCode());
        }
        log.info("{} ret.toString:{}",this.getClass().getSimpleName(),ret.build().toString());
        handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
    }
    /**
     * 获得build
     * @return
     */
    public User.UpdateLoginPasswordReq.Builder getBuild(){
        return User.UpdateLoginPasswordReq.newBuilder();
    }

    /**
     * 组装参数
     * @param builder
     * @return
     */
    protected Map<String,Object> param(User.UpdateLoginPasswordReq.Builder builder){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("pwd",builder.getOldPwd());
        paramMap.put("newPwd",builder.getNewPwd());
        paramMap.put("userName",builder.getUserName());
        return paramMap;
    }

    /**
     * 获得请求的url
     * @return
     */
    protected String getReqUrl(){
        return UrlConstants.UPDATE_LOGIN_PASSWORD;
    }

    @Override
    protected boolean definedCheck(User.UpdateLoginPasswordReq.Builder builder,
                                   AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret) {
        return true;
    }

    /**
     * 对结果进行处理
     * @param responseInfo
     * @return
     */
    protected Void dealResult(ResponseInfo responseInfo){
        return null;
    }
}
