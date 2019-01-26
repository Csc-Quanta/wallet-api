package org.csc.wlt.helper;

import com.google.protobuf.ByteString;
import com.googlecode.protobuf.format.JsonFormat.ParseException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.beans.FramePacket;
import onight.tfw.outils.serialize.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.codehaus.jackson.map.ObjectMapper;
import org.csc.bcapi.EncAPI;
import org.csc.bcvm.utils.ByteUtil;
import org.csc.wallet.service.Wallet.*;
import org.csc.wallet.service.Wallet.RespGetBlock.Builder;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscWltAddress;
import org.csc.wlt.entity.CscWltAddressModel;
import org.csc.wlt.entity.CscWltContract;
import org.csc.wlt.entity.CscWltTx;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.enums.TransTypeEnum;
import org.csc.wlt.helper.validator.*;
import org.csc.wlt.utils.JsonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * 
 */
@NActorProvider
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "txHelper")
@Slf4j
@Data
public class TransactionHelper implements ActorService {

	@ActorRequire(name = "bc_encoder", scope = "global")
	EncAPI encApi;

	@ActorRequire(name = "commHelper", scope = "global")
	CommonHelper commonHelper;

	@ActorRequire(name = "Transaction_Impl", scope = "global")
	TransactionImpl transactionImpl;

	@ActorRequire(name = "daos", scope = "global")
	Daos daos;

	ObjectMapper mapper = new ObjectMapper();

	/*********************************************************************************************/

	/**
	 * @param address
	 * @return
	 */
	public CscWltAddress getAddress(String address) {
		CscWltAddressModel example = new CscWltAddressModel();
		example.setAddress(address);
		Object obj = daos.wltAddressDao.selectOneByExample(example);

		CscWltAddress addr = null;
		if (obj != null) {
			addr = (CscWltAddress) obj;
		}
		if (addr == null || StringUtils.isBlank(addr.getPublicKey()) || StringUtils.isBlank(addr.getPrivateKey())) {
			throw new NullPointerException("address's publicKey or privateKey is null");
		}
		return addr;
	}

	/**
	 * @param txHash
	 */
	@Deprecated
	public void insertTxHash(String txHash) {
		CscWltTx tx = new CscWltTx();
		tx.setTxId(UUIDGenerator.generate());
		tx.setTxHash(txHash);
		tx.setCreatedTime(new Date());
		tx.setUpdatedTime(tx.getCreatedTime());
		try {
			daos.wltTxDao.insert(tx);
		} catch (Exception e) {
			log.error("save tx to db error");
		}
	}

	/**
	 * @param contractAddress
	 * @param contractTxHash
	 */
	@Deprecated
	public void insertContract(String contractAddress, String contractTxHash) {
		CscWltContract contract = new CscWltContract();
		contract.setContractAddress(contractAddress);
		contract.setContractTxHash(contractTxHash);
		contract.setContractId(UUIDGenerator.generate());
		contract.setCreatedTime(new Date());
		contract.setUpdatedTime(contract.getCreatedTime());
		try {
			daos.wltContractDao.insert(contract);
		} catch (Exception e) {
			log.error("save contract to db error");
		}
	}

	public void queryTransaction(final FramePacket pack, final BaseData pb, RespGetTxByHash.Builder ret)
			throws ParseException {
		String[] str = commonHelper.getDecryptData(pb, pb.getBusi());
		if (str[0] != null) {
//			ret.set(str[0]);
			return;
		}

		ReqGetTxByHash.Builder req = ReqGetTxByHash.newBuilder();
		JsonUtil.jsonToObject(str[1], req);
		if (StringUtils.isBlank(req.getHash())) {
//			ret.setRplMsg("hexTxHash is null");
			log.warn("no txhash");
		}
		transactionImpl.findTransactionByHash(req.build(), ret);
		if (ret == null) {
			log.warn("query transaction error");
//			ret.setRplMsg(String.format("the busi[%s] data is null ", pb.getData()));
			return;
		}

	}

	public void createTransaction(FramePacket pack, BaseData pb, RespCreateTransaction.Builder ret)
			throws ParseException {

		String[] str = commonHelper.getDecryptData(pb, pb.getBusi());
		if (str[0] != null) {
			ret.setRplMsg(str[0]);
			return;
		}

		ReqCreateMultiTransaction.Builder req = ReqCreateMultiTransaction.newBuilder();
		JsonUtil.jsonToObject(str[1], req);
		
		transactionHandle(req.getTransaction(), ret);
//		ret.setRplCode(ReturnCodeEnum.DONE.getCode());

	}

	public void transactionHandle(MultiTransactionImpl reqTransaction, RespCreateTransaction.Builder ret) {

		MultiTransactionBodyImpl reqBodyImpl = reqTransaction.getTxBody();
		if(reqBodyImpl == null) {

			return ;
		}
		if(reqBodyImpl.getType() == TransTypeEnum.TYPE_DEFAULT.value()) {
			isVerify(reqTransaction, new TxDefaultValidator(), ret);;
		}else if(reqBodyImpl.getType() == TransTypeEnum.TYPE_CreateContract.value()){
			isVerify(reqTransaction, new TxCreateContractValidator(), ret);
		}else if(reqBodyImpl.getType() == TransTypeEnum.TYPE_CallContract.value()){
			isVerify(reqTransaction, new TxDoContractValidator(), ret);
		}else if(reqBodyImpl.getType() == TransTypeEnum.TYPE_CreateToken.value()){
			isVerify(reqTransaction, new TxCreateTokenValidator(), ret);
		}else if(reqBodyImpl.getType() == TransTypeEnum.TYPE_TokenTransaction.value()){
			isVerify(reqTransaction, new TxSendTokenValidator(), ret);
		}else if(reqBodyImpl.getType() == TransTypeEnum.TYPE_CreateCryptoToken.value()){
			isVerify(reqTransaction, new TxCreateCryptoValidator(), ret);
		}else if(reqBodyImpl.getType() == TransTypeEnum.TYPE_CryptoTokenTransaction.value()){
			isVerify(reqTransaction, new TxSendCryptoValidator(), ret);
		}else {
			ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("error transaction type ");
			return ;
		}

		if(ret.getRplCode() == ReturnCodeEnum.VALIDATION.getCode()) {
			return ;
		}
		
		/**
		 * 接收对象为 transactionImpl 先转成 transaction 进行签名 再转成 transactionImpl 发送
		 */

		MultiTransactionBody.Builder oBody = MultiTransactionBody.newBuilder();
		// 构建 oBody

		List<MultiTransactionInputImpl> reqInputsImpl = reqBodyImpl.getInputsList();

		for (MultiTransactionInputImpl reqInputImpl : reqInputsImpl) {
			BigDecimal reqAmount = StringUtils.isEmpty(reqInputImpl.getAmount()) ? new BigDecimal("0")
					: new BigDecimal(reqInputImpl.getAmount());
			MultiTransactionInput.Builder oInput = MultiTransactionInput.newBuilder();
			oInput.setAddress(ByteString.copyFrom(encApi.hexDec(reqInputImpl.getAddress())));
			oInput.setAmount(ByteString.copyFrom(ByteUtil.bigIntegerToBytes(reqAmount.toBigInteger())));
			oInput.setToken(reqInputImpl.getToken()+"");
			oInput.setNonce(reqInputImpl.getNonce());
			oInput.setSymbol(reqInputImpl.getSymbol()+"");
			oInput.setCryptoToken(StringUtils.isBlank(reqInputImpl.getCryptoToken())? ByteString.EMPTY :ByteString.copyFrom(encApi.hexDec(reqInputImpl.getCryptoToken())));
			oBody.setType(oBody.getType());
			oBody.addInputs(oInput);
		}

		if (reqBodyImpl.getOutputsList() != null ) {
			List<MultiTransactionOutputImpl> reqOutputsImpl = reqBodyImpl.getOutputsList();
			for (MultiTransactionOutputImpl reqOutputImpl : reqOutputsImpl) {
				String reqAddress = reqOutputImpl.getAddress();
				BigDecimal reqAmount = StringUtils.isEmpty(reqOutputImpl.getAmount()) ? new BigDecimal("0")
						: new BigDecimal(reqOutputImpl.getAmount());
				MultiTransactionOutput.Builder oOutput = MultiTransactionOutput.newBuilder();
				oOutput.setAddress(ByteString.copyFrom(encApi.hexDec(reqAddress)));
				oOutput.setAmount(ByteString.copyFrom(ByteUtil.bigIntegerToBytes(reqAmount.toBigInteger())));
				oOutput.setCryptoToken(StringUtils.isNotBlank(reqOutputImpl.getCryptoToken()) ? ByteString.copyFrom(encApi.hexDec(reqOutputImpl.getCryptoToken()))
								: ByteString.EMPTY);
				oOutput.setSymbol(StringUtils.isNotBlank(reqOutputImpl.getSymbol()) ? reqOutputImpl.getSymbol() : "");
				oBody.addOutputs(oOutput);
			}
		} 

		if (StringUtils.isNotBlank(reqBodyImpl.getData())) {
			// 上链的数据，合约中为处理的代码，ERC721是个性化信息，Token无数据
			// 主要为合约代码，创建721Token，其他作用暂不明确 T O D O
			oBody.setData(ByteString.copyFrom(encApi.hexDec(reqBodyImpl.getData())));
		}else  if(reqBodyImpl.getCryptoTokenData()!=null) {
			// CryptoTokenData JSON转成Protobuf Data
				oBody.setData(ByteString.copyFrom(reqBodyImpl.getCryptoTokenData().toByteArray()));

		}

		
		if (StringUtils.isNotBlank(reqBodyImpl.getExdata())) {
			//上链，附加信息，可能都有
			oBody.setExdata(ByteString.copyFrom(encApi.hexDec(reqBodyImpl.getExdata())));
		}
//		else {
//			oBody.setExdata(ByteString.copyFrom(encApi.hexDec(reqBodyImpl.getInputs(0).getAddress())));
//			
//		}

		// 暂时不用
		if (reqBodyImpl.getDelegateList() != null && !reqBodyImpl.getDelegateList().isEmpty()) {
			for (String str : reqBodyImpl.getDelegateList()) {
				oBody.addDelegate(ByteString.copyFrom(encApi.hexDec(str)));
			}
		}
		oBody.setType(reqBodyImpl.getType());
		oBody.setTimestamp(System.currentTimeMillis());
		CscWltAddress addressEntity = getAddress(reqInputsImpl.get(0).getAddress());
		byte[] sign = encApi.ecSign(addressEntity.getPrivateKey(), oBody.build().toByteArray());
//		log.debug("signature is :--->" + encApi.hexEnc(sign));
//		boolean flag = encApi.ecVerify(addressEntity.getPublicKey(), oBody.build().toByteArray(), sign);
		MultiTransactionSignatureImpl.Builder sendSignature = MultiTransactionSignatureImpl
				.newBuilder();
		sendSignature.setSignature(encApi.hexEnc(sign));
		
		MultiTransactionBodyImpl.Builder sendbody = reqTransaction.getTxBody().toBuilder();
		sendbody.addSignatures(sendSignature)
		.setTimestamp(oBody.getTimestamp())
		.setData(encApi.hexEnc(oBody.getData().toByteArray()));
		MultiTransactionImpl.Builder sendTx  = reqTransaction.toBuilder();
		sendTx.setTxBody(sendbody);
		
		ReqCreateMultiTransaction.Builder sendCreate = ReqCreateMultiTransaction.newBuilder();
		sendCreate.setTransaction(sendTx);

		transactionImpl.sendTransaction(sendCreate.build(), ret);
	}
	
	
	private void isVerify(MultiTransactionImpl reqTransaction, TxValidator validator,
			org.csc.wallet.service.Wallet.RespCreateTransaction.Builder ret) {
		validator.isVerify(reqTransaction, ret);
	}

	public void queryLastBlock(RespBlockDetail.Builder ret) {
		
		transactionImpl.queryLastBlock(ret);
	}

	public void queryBlockByTxHash(FramePacket pack, BaseData pb, Builder ret) throws ParseException {
		String[] str = commonHelper.getDecryptData(pb, pb.getBusi());
		if (str[0] != null) {
//			ret.set(str[0]);
			return;
		}

		ReqGetTxByHash.Builder req = ReqGetTxByHash.newBuilder();
		JsonUtil.jsonToObject(str[1], req);
		if (StringUtils.isBlank(req.getHash())) {
//			ret.setRplMsg("hexTxHash is null");
			log.warn("no txhash");
		}
		transactionImpl.findTxBlockByHash(req.build(), ret);
		if (ret == null) {
			log.warn("query transaction error");
//			ret.setRplMsg(String.format("the busi[%s] data is null ", pb.getData()));
			return;
		}
	}

}
