package org.csc.wlt.web.cache;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import onight.oapi.scala.commons.SessionModules;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.async.CompleteHandler;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;
import org.csc.wallet.service.Cache;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.utils.ResultUtil;

@NActorProvider
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class EcacheFlush extends SessionModules<Wallet.BaseData> {

	@ActorRequire(name = "resultUtil", scope = "global")
	ResultUtil resultUtil;

	@Override
	public String[] getCmds() {
		return new String[] { Cache.PWLTCacheCommand.PSS.name() };
	}

	@Override
	public String getModule() {
		return Cache.PWLTCacheModule.WTA.name();
	}

	@Override
	public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
		Cache.PRetCommon.Builder ret = Cache.PRetCommon.newBuilder();
		try{
			resultUtil.cleanAndPut();
			ret.setCode(1);
		}catch (Exception e){
			log.error("刷新缓存失败 error:",e);
			ret.setCode(-1);
		}
		handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
	}
}
