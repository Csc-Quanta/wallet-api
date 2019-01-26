package org.csc.wlt.web.message;

import com.googlecode.protobuf.format.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.async.CompleteHandler;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.helper.SendMsgHelp;
import org.csc.wlt.model.AppReqHeader;
import org.csc.wlt.service.impl.CommonCheckService;

@NActorProvider
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class VerifySmsCode extends CommonCheckService<Wallet.BaseData,User.VerifySmsCodeReq.Builder> {
    @ActorRequire(name = "sendMsgHelp", scope = "global")
    SendMsgHelp sendMsgHelp;

    @Override
    public String[] getCmds() {
        return new String[]{User.PUSERCommand.VSM.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }
    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        ResultOuterClass.Result.Builder ret = ResultOuterClass.Result.newBuilder();
        User.VerifySmsCodeRes.Builder builder = User.VerifySmsCodeRes.newBuilder();
        try {
            User.VerifySmsCodeReq.Builder req = getBuild();
            String smsPassToken;
            if(!check(pack,pb,ret,req) || (smsPassToken = sendMsgHelp.checkSmsCode(req,pack,ret))==null){
                log.info("{} ret.toString:{}",this.getClass().getSimpleName(),ret.build().toString());
                handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
                return;
            }
            builder.setRplCode(ReturnCodeEnum.DONE.getCode());
            builder.setSmsPassToken(smsPassToken);
        } catch (JsonFormat.ParseException e) {
            log.error("user sendMsg fail param :{} :{}",pb.toString(),e);
            builder.setRplCode(ReturnCodeEnum.ERROR.getCode());
        }
        log.info("{} builder.toString:{}",this.getClass().getSimpleName(),builder.build().toString());
        handler.onFinished(PacketHelper.toPBReturn(pack, builder.build()));
    }

    @Override
    protected boolean definedCheck(User.VerifySmsCodeReq.Builder builder, AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret) {
        return true;
    }

    @Override
    protected User.VerifySmsCodeReq.Builder getBuild() {
        return User.VerifySmsCodeReq.newBuilder();
    }
}
