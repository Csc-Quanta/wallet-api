package org.csc.wlt.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.async.CompleteHandler;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;
import org.csc.wlt.common.CaCheConstants;
import org.csc.wlt.common.Constants;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.utils.UserCacheUtil;

@NActorProvider
@Slf4j
@Data
@Instantiate(name = "userLoginFilter")
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
public class UserLoginFilter extends AbBusinessFilter implements ActorService {

    @ActorRequire(name = "userCacheUtil", scope = "global")
    UserCacheUtil userCacheUtil;

    @Override
    public boolean preRoute(FramePacket pack, CompleteHandler handler) {
        log.info("pack.getModuleAndCMD():{}",pack.getModuleAndCMD());
        String grantToken = pack.getHttpServerletRequest().getHeader(Constants.GRANT_TOKEN);
        boolean checkResult = userCacheUtil.checkAndPut(CaCheConstants.USER_TOKEN_PRX,grantToken);
        if(!checkResult){
            ResultOuterClass.Result.Builder builder = ResultOuterClass.Result.newBuilder();
            builder.setRplCode(ReturnCodeEnum.LOGIN_TIME_OUT.getCode());
            builder.setRplMsg(ReturnCodeEnum.LOGIN_TIME_OUT.getMsg());
            log.info("UserLoginFilter return builder:{}",builder.build().toString());
            handler.onFinished(PacketHelper.toPBReturn(pack, builder.build()));
        }
        return checkResult;
    }

    @Override
    public boolean onComplete(FramePacket pack) {
        return true;
    }

    @Override
    public boolean postRoute(FramePacket pack,CompleteHandler handler) {
        return true;
    }
}
