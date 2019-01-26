package org.csc.wlt.web.transactions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.async.CompleteHandler;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.helper.validator.TxDefaultValidator;


@NActorProvider
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class DefaultTransactionProcessor extends TransactionProcessorSessionModules<Wallet.BaseData> {

    @Override
    public String[] getCmds() {
        return new String[]{Wallet.PWLTCommand.TXD.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }

    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        Wallet.RespCreateTransaction.Builder ret = transaction(pb);
        handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
    }


    @Override
    protected boolean verifyRequestData(Wallet.MultiTransactionImpl txImpl, Wallet.RespCreateTransaction.Builder ret) {
        if (txImpl == null || txImpl.getTxBody() == null) {
            ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode());
            ret.setRplMsg("tx body is null");
            return false;
        }
        new TxDefaultValidator().isVerify(txImpl, ret);
        // if(ReturnCodeEnum.DONE.getCode()==ret.getRet()){
        return ret.getRplCode() != ReturnCodeEnum.VALIDATION.getCode();

    }
}
