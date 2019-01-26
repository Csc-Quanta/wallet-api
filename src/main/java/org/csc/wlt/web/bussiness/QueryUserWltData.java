package org.csc.wlt.web.bussiness;

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
import org.csc.wlt.model.AppReqHeader;
import org.csc.wlt.service.WalletDataService;
import org.csc.wlt.service.impl.CommonCheckService;

@NActorProvider
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class QueryUserWltData extends CommonCheckService<Wallet.BaseData, User.QueryUserWltDataReq.Builder> {
    @ActorRequire(name = "walletDataService", scope = "global")
    WalletDataService walletDataService;

    @Override
    public String[] getCmds() {
        return new String[]{User.PUSERCommand.QUW.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }

    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        ResultOuterClass.Result.Builder ret = ResultOuterClass.Result.newBuilder();
        User.QueryUserWltDataRes.Builder result = User.QueryUserWltDataRes.newBuilder();
        try {
            User.QueryUserWltDataReq.Builder req = getBuild();
            if(!check(pack,pb,ret,req)|| !walletDataService.checkUserId(req.getUserId(),getUserId(pack),ret)){
                log.info("{} ret.toString:{}",this.getClass().getSimpleName(),ret.build().toString());
                handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
                return;
            }
            walletDataService.queryUserWltData(req,commonHelper.getHeader(pack).getAppId(),result);
        } catch (Exception e) {
            log.error("user sendMsg fail param :{} :{}",pb.toString(),e);
            result.setRplCode(ReturnCodeEnum.ERROR.getCode());
        }
        log.info("{} result.toString:{}",this.getClass().getSimpleName(),result.build().toString());
        handler.onFinished(PacketHelper.toPBReturn(pack, result.build()));
    }


    @Override
    protected boolean definedCheck(User.QueryUserWltDataReq.Builder builder, AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret) {
        return true;
    }

    @Override
    protected User.QueryUserWltDataReq.Builder getBuild() {
        return User.QueryUserWltDataReq.newBuilder();
    }
}
