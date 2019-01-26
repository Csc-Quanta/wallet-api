package org.csc.wlt.web.bussiness;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.async.CompleteHandler;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;
import org.apache.commons.collections4.CollectionUtils;
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
public class UploadUserWltData extends CommonCheckService<Wallet.BaseData, User.UploadUserWltDataReq.Builder> {
    @ActorRequire(name = "walletDataService", scope = "global")
    WalletDataService walletDataService;
    @Override
    public String[] getCmds() {
        return new String[]{User.PUSERCommand.UWD.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }

    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        ResultOuterClass.Result.Builder ret = ResultOuterClass.Result.newBuilder();
        try {
            User.UploadUserWltDataReq.Builder builder = getBuild();
            if(!check(pack,pb,ret,builder) || !walletDataService.checkUserId(builder.getUserId(),getUserId(pack),ret)
                || !walletDataService.doUploadUserWltData(builder,commonHelper.getHeader(pack).getAppId(),ret));
        } catch (Exception e) {
            log.error("user sendMsg fail param :{} :{}",pb.toString(),e);
            ret.setRplCode(ReturnCodeEnum.ERROR.getCode());
        }
        log.info("{} ret.toString:{}",this.getClass().getSimpleName(),ret.build().toString());
        handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
    }
    @Override
    protected boolean definedCheck(User.UploadUserWltDataReq.Builder builder, AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret) {
        if(CollectionUtils.isEmpty(builder.getWalletDataBuilderList())){
            //如果是空的 默认是成功
            log.info("{} definedCheck fail",this.getClass().getSimpleName());
            ret.setRplCode(ReturnCodeEnum.DONE.getCode());
            return false;
        }
        return true;
    }
    @Override
    protected User.UploadUserWltDataReq.Builder getBuild() {
        return User.UploadUserWltDataReq.newBuilder();
    }
}
