package org.csc.wlt.web;

import lombok.EqualsAndHashCode;
import org.csc.wallet.service.Wallet.BaseData;
import org.csc.wallet.service.Wallet.PWLTCommand;
import org.csc.wallet.service.Wallet.PWLTModule;
import org.csc.wallet.service.Wallet.RespBlockDetail;
import org.csc.wlt.dao.Daos;
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
public class QueryLastBlock extends SessionModules<BaseData> {

	@ActorRequire(name = "txHelper", scope = "global")
	TransactionHelper txHelper;

	@ActorRequire(name = "daos", scope = "global")
	Daos daos;

	@Override
	public String[] getCmds() {
		return new String[] { PWLTCommand.LBQ.name() };
	}

	@Override
	public String getModule() {
		return PWLTModule.WLT.name();
	}

	@Override
	public void onPBPacket(final FramePacket pack, final BaseData pb, final CompleteHandler handler) {
		RespBlockDetail.Builder ret = RespBlockDetail.newBuilder();
		try {
			txHelper.queryLastBlock(ret);
		} catch (Exception e) {
			log.error("get last block error : " + e.getMessage());
			ret.setRplCode(-1).setRplMsg("get last block error");
		}

		handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
	}
}
