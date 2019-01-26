package org.csc.wlt.helper;

import com.google.protobuf.GeneratedMessageV3.Builder;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.IPacketSender;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.Wallet.*;
import org.csc.wlt.enums.NodeInterfaceEnum;
import org.csc.wlt.utils.JsonUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

/**
 * 
 */
@NActorProvider
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "Transaction_Impl")
@Slf4j
@Data
public class TransactionImpl implements ActorService{
	@ActorRequire(name = "http", scope = "global")
	IPacketSender sender;

	@ActorRequire(name = "commHelper", scope = "global")
	CommonHelper commonHelper;

	BigDecimal ws = new BigDecimal("1000000000000000000");

	/***
	 * 创建交易
	 * @return
	 */
	public void sendTransaction(ReqCreateMultiTransaction create, RespCreateTransaction.Builder ret) {
		ResultOuterClass.OldResult.Builder oldResult = getBuilder();
		sendTx(create,ret,oldResult,NodeInterfaceEnum.CREATE_TRANSACTION);
		ret.setRplCode(oldResult.getRetCode());
		ret.setRplMsg(oldResult.getRetMsg());
	}
	/**
	 * 按哈希查找交易
	 *
	 * @param pb
	 * @return
	 */
	public void findTransactionByHash(ReqGetTxByHash pb, RespGetTxByHash.Builder ret) {
		ResultOuterClass.OldResult.Builder oldResult = getBuilder();
		sendTx(pb,ret,oldResult,NodeInterfaceEnum.QUERY_TRANSACTION);
		ret.setRplCode(oldResult.getRetCode());
		ret.setRplMsg(oldResult.getRetMsg());
	}

	/**
	 * 获得builder
	 * @return
	 */
	private ResultOuterClass.OldResult.Builder getBuilder(){
		return ResultOuterClass.OldResult.newBuilder();
	}
	/**
	 * 发送交易
	 * @param create
	 * @param ret
	 * @param oldResult
	 * @param <T>
	 * @param <D>
	 */
	public <T extends Message,D extends Builder> void sendTx(T create,
						D ret,ResultOuterClass.OldResult.Builder oldResult,NodeInterfaceEnum nodeInterfaceEnum){
		String sendJson = new JsonFormat().printToString(create);
		String[] nodes = commonHelper.getNodeList();
		if (nodes == null) {
			oldResult.setRetCode(-2).setRetMsg("node is null");
			return;
		}
		for (String s : nodes) {
			String url = commonHelper.getParamValue(nodeInterfaceEnum.getValue());
			FramePacket fposttx = PacketHelper.buildUrlFromJson(sendJson, "POST", s + url);
			log.info("url:" + s + url + ";json:" + sendJson);
			val body = commonHelper.send(fposttx);
			if (body.getBody() == null) {
				log.warn("chain return data is null ");
				oldResult.setRetCode(-2).setRetMsg("response body is null ");
				continue;
			}
			log.info("sendTx return data :{}",new String(body.getBody(), Charset.defaultCharset()));
			try {
				JsonUtil.jsonToObject(body.getBody(), ret);
				JsonUtil.jsonToObject(body.getBody(), oldResult);
				break;
			} catch (IOException e) {
				log.error("createTransaction error JsonUtil.jsonToObject==> ", e);
				oldResult.setRetCode(-2).setRetMsg("jsonToObject error ");
				continue;
			}
		}
	}


	public void queryLastBlock(RespBlockDetail.Builder ret) {

		String[] nodes = commonHelper.getNodeList();
		if (nodes == null) {
			ret.setRplCode(-2).setRplMsg("node is null");
			return;
		}
		for (String s : nodes) {
			String url = commonHelper.getParamValue(NodeInterfaceEnum.QUERY_LASTBLOCK.getValue());
			FramePacket fposttx = PacketHelper.buildUrlForGet(s + url);
			log.info("url:" + s + url);
			val body = commonHelper.send(fposttx);
			if (body.getBody() == null) {
				log.warn("chain return data is null ");
				ret.setRplCode(-2).setRplMsg("response body is null ");
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
				log.error("createTransaction error JsonUtil.jsonToObject==> ", e);
				ret.setRplCode(-2).setRplMsg("jsonToObject error ");
				continue;
			}
		}

	}

	/**
	 * 按哈希查找交易
	 * 
	 * @param pb
	 * @return
	 */
	public void findTxBlockByHash(ReqGetTxByHash pb, RespGetBlock.Builder ret) {

		String sendJson = new JsonFormat().printToString(pb);
		String[] nodes = commonHelper.getNodeList();
		if (nodes == null) {
			ret.setRplCode(-2).setRplMsg("node is null");
			return;
		}
		for (String s : nodes) {
			String url = commonHelper.getParamValue(NodeInterfaceEnum.QUERY_TXBLOCK.getValue());
			FramePacket fposttx = PacketHelper.buildUrlFromJson(sendJson, "POST", s + url);
			log.info("url:" + s + url + ";json:" + sendJson);
			val body = commonHelper.send(fposttx);
			if (body.getBody() == null) {
				log.warn("chain return data is null ");
				ret.setRplCode(-2).setRplMsg("response body is null ");
				continue;
			}
			try {
				JsonUtil.jsonToObject(body.getBody(), ret);
				break;
			} catch (IOException e) {
				log.error("createTransaction error JsonUtil.jsonToObject==> ", e);
				ret.setRplCode(-2).setRplMsg("jsonToObject error ");
				continue;
			}
		}
	}


}
