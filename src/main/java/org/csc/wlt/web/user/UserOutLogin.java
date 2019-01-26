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
import org.csc.wlt.common.CaCheConstants;
import org.csc.wlt.common.ErrorCodeConstants;
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
public class UserOutLogin extends AbTemplateService<Wallet.BaseData,Void, User.UserOutLoginReq.Builder> {
    @Override
    public String[] getCmds() {
        return new String[]{User.PUSERCommand.UOL.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }

    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        ResultOuterClass.Result.Builder ret = ResultOuterClass.Result.newBuilder();
        try {
            //调用用户中心，不管成功失败都进行清除token
            execute(pb,pack,ret);
            ret.setRplCode(ErrorCodeConstants.SUC);
            userCacheUtil.remove(CaCheConstants.USER_TOKEN_PRX,commonHelper.getHeader(pack).getGrantToken());
        } catch (JsonFormat.ParseException e) {
            log.error("user userExist fail param :{} :{}",pb.toString(),e);
            ret.setRplCode(ReturnCodeEnum.ERROR.getCode());
        }
        handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
    }


    @Override
    protected User.UserOutLoginReq.Builder getBuild() {
        return User.UserOutLoginReq.newBuilder();
    }

    @Override
    protected Map<String, Object> param(User.UserOutLoginReq.Builder builder) {
        return new HashMap<>();
    }

    @Override
    protected String getReqUrl() {
        return UrlConstants.USER_OUT_LOGIN;
    }

    @Override
    protected boolean definedCheck(User.UserOutLoginReq.Builder builder,
                                   AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret) {
        return true;
    }

    @Override
    protected Void dealResult(ResponseInfo responseInfo) {
        return null;
    }
}
