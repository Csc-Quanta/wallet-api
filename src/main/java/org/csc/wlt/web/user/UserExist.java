package org.csc.wlt.web.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import onight.oapi.scala.commons.SessionModules;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.async.CompleteHandler;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.service.impl.AbTemplateService;

@NActorProvider
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class UserExist extends SessionModules<Wallet.BaseData> {

    @ActorRequire(name = "userExistService", scope = "global")
    AbTemplateService<Wallet.BaseData,String,User.UserExistReq.Builder> userExistService;
    @Override
    public String[] getCmds() {
        return new String[]{User.PUSERCommand.UEX.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }

    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        ResultOuterClass.Result.Builder ret = ResultOuterClass.Result.newBuilder();
        User.UserExistRes.Builder builder = User.UserExistRes.newBuilder();
        try {
            String existResult = userExistService.execute(pb,pack,ret);
            if(existResult == null ){
                log.info("{} ret.toString:{}",this.getClass().getSimpleName(),ret.build().toString());
                handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
                return;
            }
            builder.setRplCode(ReturnCodeEnum.DONE.getCode());
            builder.setFlag(existResult);
        } catch (Exception e) {
            log.error("user userExist fail param :{} :{}",pb.toString(),e);
            builder.setRplCode(ReturnCodeEnum.ERROR.getCode());
        }
        log.info("{} builder.toString:{}",this.getClass().getSimpleName(),builder.build().toString());
        handler.onFinished(PacketHelper.toPBReturn(pack, builder.build()));
    }


}
