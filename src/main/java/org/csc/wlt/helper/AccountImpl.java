package org.csc.wlt.helper;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.Wallet.ReqGetAccount;
import org.csc.wallet.service.Wallet.RespGetAccount;
import org.csc.wlt.enums.NodeInterfaceEnum;
import org.csc.wlt.utils.JsonUtil;

import com.googlecode.protobuf.format.JsonFormat;

import lombok.Data;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;

/**
 * 
 */
@NActorProvider
@Slf4j
@Data
@Instantiate(name = "Account_Impl")
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
public class AccountImpl implements ActorService {

	@ActorRequire(name = "commHelper", scope = "global")
	CommonHelper commonHelper;

	BigDecimal ws = new BigDecimal("1000000000000000000");

	/**
	 * 查询用户
	 *
	 * @param address
	 * @return
	 */
	public void getAccountInfo(ReqGetAccount address, RespGetAccount.Builder ret) {
		String sendJson = new JsonFormat().printToString(address);
		log.info("sendJson=" + sendJson);
		String[] nodes = commonHelper.getNodeList();
		if (nodes == null) {
			ret.setRplCode(-2);
			return;
		}
		for (String s : nodes) {
			String url = commonHelper.getParamValue(NodeInterfaceEnum.QUERY_ADDRESS.getValue());
			FramePacket fposttx = PacketHelper.buildUrlFromJson(sendJson, "POST", s + url);
			val body = commonHelper.send(fposttx);
			if (body.getBody() == null) {
				log.warn("chain return data is null ");
				ret.setRplCode(-2);
				continue;
			}

			try {
				ResultOuterClass.OldResult.Builder oldResult = ResultOuterClass.OldResult.newBuilder();
				JsonUtil.jsonToObject(body.getBody(), ret);
				JsonUtil.jsonToObject(body.getBody(), oldResult);
				ret.setRplCode(oldResult.getRetCode());
				ret.setRplMsg(oldResult.getRetMsg());
				break;
			} catch (IOException e) {
				e.printStackTrace();
				log.error("getAccountInfo error ");
				ret.setRplCode(-2);
				return;
			}
		}

	}
}
