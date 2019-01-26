package org.csc.wlt.web;

import lombok.EqualsAndHashCode;
import org.csc.wallet.service.Wallet.BaseData;
import org.csc.wallet.service.Wallet.PWLTCommand;
import org.csc.wallet.service.Wallet.PWLTModule;
import org.csc.wallet.service.Wallet.RespGetTxByHash;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.helper.TransactionHelper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.oapi.scala.commons.SessionModules;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.async.CompleteHandler;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;

@NActorProvider
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class QueryTransaction extends SessionModules<BaseData> {

	@ActorRequire(name = "txHelper", scope = "global")
	TransactionHelper txHelper;

	@ActorRequire(name = "daos", scope = "global")
	Daos daos;

	@Override
	public String[] getCmds() {
		return new String[] { PWLTCommand.QTS.name() };
	}

	@Override
	public String getModule() {
		return PWLTModule.WLT.name();
	}

	@Override
	public void onPBPacket(final FramePacket pack, final BaseData pb, final CompleteHandler handler) {
		RespGetTxByHash.Builder ret = RespGetTxByHash.newBuilder();
		ret.setRplCode(ReturnCodeEnum.ERROR.getCode());
		try {
			txHelper.queryTransaction(pack, pb, ret);
		} catch (Exception e) {
			ret.setRplCode(-2);
			ret.setRplMsg(new StringBuffer(ReturnCodeEnum.EXCEPTION.getMsg()).append(":").append(e.getMessage()).toString());
			log.error("registerAddress exception==>", e);
		}

		handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
	}

}
