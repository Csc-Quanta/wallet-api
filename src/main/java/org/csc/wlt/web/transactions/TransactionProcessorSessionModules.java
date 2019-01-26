package org.csc.wlt.web.transactions;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.ProtocolStringList;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.oapi.scala.commons.SessionModules;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import org.apache.commons.lang3.StringUtils;
import org.csc.bcapi.EncAPI;
import org.csc.bcvm.utils.ByteUtil;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscWltAddress;
import org.csc.wlt.entity.CscWltAddressModel;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.helper.CommonHelper;
import org.csc.wlt.helper.TransactionImpl;
import org.csc.wlt.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


/**
 * 模板模式
 */


@Data
@EqualsAndHashCode(callSuper=false)
public abstract class TransactionProcessorSessionModules<T extends Message> extends SessionModules<T>{
    Logger log = LoggerFactory.getLogger(TransactionProcessorSessionModules.class);

    @ActorRequire(name = "bc_encoder", scope = "global")
    EncAPI encApi;

    @ActorRequire(name = "commHelper", scope = "global")
    CommonHelper commonHelper;

    @ActorRequire(name = "Transaction_Impl", scope = "global")
    TransactionImpl transactionImpl;

    @ActorRequire(name = "daos", scope = "global")
    Daos daos;

    /**
     * 交易
     *
     * @param pb
     */
    public Wallet.RespCreateTransaction.Builder transaction(T pb) {
        Wallet.RespCreateTransaction.Builder ret = Wallet.RespCreateTransaction.newBuilder();
        ret.setRplCode(ReturnCodeEnum.ERROR.getCode());

        // 解密
        Wallet.ReqCreateMultiTransaction.Builder req = null;
        try {
            req = decryptData(pb, ret);
        } catch (Exception e) {
            ret.setRplCode(ReturnCodeEnum.EXCEPTION.getCode())
                    .setRplMsg(new StringBuffer(ReturnCodeEnum.EXCEPTION.getMsg())
                            .append(":")
                            .append(e.getMessage())
                            .toString());
            log.error("JsonFormat ParseException ", e);
        }
        if(req == null){
            log.error("decryptData is failed return：{}",ret.toString());
            return ret;
        }
        // 校验参数
        Wallet.MultiTransactionImpl reqTrans = req.getTransaction();
        if (!verifyRequestData(reqTrans, ret)) {
            log.error("createTransaction exception==>{}", ret.getRplMsg());
            return ret;
        }

        // 构建请求
        Wallet.MultiTransactionBody.Builder oBody = processReq(reqTrans, ret);

        //发送请
        sendToNode(reqTrans.getTxBody().getInputsList().get(0).getAddress(), oBody, reqTrans, ret);

        return ret;
    }


    /**
     * 解密， 无需抽象，解密为统一方式
     *
     * @param pba
     * @return
     */
    public Wallet.ReqCreateMultiTransaction.Builder decryptData(T pba, Wallet.RespCreateTransaction.Builder ret)
            throws JsonFormat.ParseException, InvalidProtocolBufferException {
        Wallet.BaseData pb = Wallet.BaseData.parseFrom(pba.toByteArray());
        String[] str = commonHelper.getDecryptData(pb, pb.getBusi());
        if (str[0] != null) {
            ret.setRplMsg(str[0]);
            return null;
        }

        Wallet.ReqCreateMultiTransaction.Builder req = Wallet.ReqCreateMultiTransaction.newBuilder();
        JsonUtil.jsonToObject(str[1], req);
        return req;
    }

    /**
     * 创建请求Body
     *
     * @param reqTrans
     * @param ret
     * @return
     */
    protected Wallet.MultiTransactionBody.Builder processReq(Wallet.MultiTransactionImpl reqTrans, Wallet.RespCreateTransaction.Builder ret) {
        Wallet.MultiTransactionBody.Builder oBody = Wallet.MultiTransactionBody.newBuilder();
        Wallet.MultiTransactionBodyImpl txBody = reqTrans.getTxBody();

        processPayer(txBody, ret, oBody);

        if (isNotEmpty(txBody.getOutputsList())) {
            processReceiver(reqTrans, ret, oBody);
        }

        if (isNotBlank(txBody.getData())) {
            // 上链数据，合约中为处理的代码，ERC721是个性化信息，Token无数据
            // 主要为合约代码，创建721Token，投票信息，仲裁信息等信息
            oBody.setData(ByteString.copyFrom(encApi.hexDec(txBody.getData())));
        } else if (txBody.getCryptoTokenData() != null) {
            // CryptoTokenData JSON转成ProtoBuf ByteString
            byte[] cryptoTokenData = txBody.getCryptoTokenData().toByteArray();
            oBody.setData(ByteString.copyFrom(cryptoTokenData));
        }

        if (StringUtils.isNotBlank(txBody.getExdata())) {
            //上链数据，附加信息
            oBody.setExdata(ByteString.copyFrom(encApi.hexDec(txBody.getExdata())));
        }

        // 暂时不用
        ProtocolStringList delegateList = txBody.getDelegateList();
        if (isNotEmpty(delegateList)) {
            for (String str : delegateList) {
                oBody.addDelegate(ByteString.copyFrom(encApi.hexDec(str)));
            }
        }
        oBody.setType(txBody.getType());
        oBody.setTimestamp(System.currentTimeMillis());
        return oBody;
    }


    /**
     * 请求Account，发送交易请求
     *
     * @param address
     * @param oBody
     * @param reqTrans
     * @param ret
     */
    private void sendToNode(String address, Wallet.MultiTransactionBody.Builder oBody,
                            Wallet.MultiTransactionImpl reqTrans, Wallet.RespCreateTransaction.Builder ret) {
        /**
         * 获取钱包地址
         */
        CscWltAddress addressEntity = getAddress(address);

        byte[] sign = encApi.ecSign(addressEntity.getPrivateKey(), oBody.build().toByteArray());
        Wallet.MultiTransactionSignatureImpl.Builder sendSignature = Wallet.MultiTransactionSignatureImpl
                .newBuilder();
        sendSignature.setSignature(encApi.hexEnc(sign));

        Wallet.MultiTransactionBodyImpl.Builder sendbody = reqTrans.getTxBody().toBuilder();
        sendbody.addSignatures(sendSignature)
                .setTimestamp(oBody.getTimestamp())
                .setData(encApi.hexEnc(oBody.getData().toByteArray()));
        Wallet.MultiTransactionImpl.Builder sendTx = reqTrans.toBuilder();
        sendTx.setTxBody(sendbody);

        Wallet.ReqCreateMultiTransaction.Builder sendCreate = Wallet.ReqCreateMultiTransaction.newBuilder();
        sendCreate.setTransaction(sendTx);
        transactionImpl.sendTransaction(sendCreate.build(), ret);
    }


    /**
     * 获取目标地址
     *
     * @param address
     * @return
     */
    protected CscWltAddress getAddress(String address) {
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
     * 处理付款人数据
     *
     * @param reqTrans
     * @param ret
     * @param oBody
     */
    protected void processPayer(Wallet.MultiTransactionBodyImpl reqTrans,
                                Wallet.RespCreateTransaction.Builder ret,
                                Wallet.MultiTransactionBody.Builder oBody) {

        for (Wallet.MultiTransactionInputImpl reqInputImpl : reqTrans.getInputsList()) {
            BigDecimal reqAmount = StringUtils.isEmpty(reqInputImpl.getAmount()) ?
                    new BigDecimal("0") : new BigDecimal(reqInputImpl.getAmount());
            Wallet.MultiTransactionInput.Builder oInput = Wallet.MultiTransactionInput.newBuilder();
            oInput.setAddress(ByteString.copyFrom(encApi.hexDec(reqInputImpl.getAddress())))
                    .setAmount(ByteString.copyFrom(ByteUtil.bigIntegerToBytes(reqAmount.toBigInteger())))
                    .setToken(reqInputImpl.getToken() + "")
                    .setNonce(reqInputImpl.getNonce())
                    .setSymbol(reqInputImpl.getSymbol() + "")
                    .setCryptoToken(StringUtils.isBlank(reqInputImpl.getCryptoToken()) ? ByteString.EMPTY : ByteString.copyFrom(encApi.hexDec(reqInputImpl.getCryptoToken())));
            oBody.setType(oBody.getType())
                    .addInputs(oInput);
        }
    }

    /**
     * 处理收款人数据
     *
     * @param reqTrans
     * @param ret
     * @param oBody
     */
    protected void processReceiver(Wallet.MultiTransactionImpl reqTrans,
                                   Wallet.RespCreateTransaction.Builder ret,
                                   Wallet.MultiTransactionBody.Builder oBody) {
        List<Wallet.MultiTransactionOutputImpl> reqOutputsImpl = reqTrans.getTxBody().getOutputsList();
        for (Wallet.MultiTransactionOutputImpl reqOutputImpl : reqOutputsImpl) {
            String reqAddress = reqOutputImpl.getAddress();
            BigDecimal reqAmount = StringUtils.isEmpty(reqOutputImpl.getAmount()) ?
                    new BigDecimal("0") : new BigDecimal(reqOutputImpl.getAmount());
            ByteString cryptoToken = StringUtils.isNotBlank(reqOutputImpl.getCryptoToken()) ?
                    ByteString.copyFrom(encApi.hexDec(reqOutputImpl.getCryptoToken())) : ByteString.EMPTY;
            Wallet.MultiTransactionOutput.Builder oOutput = Wallet.MultiTransactionOutput.newBuilder()
                    .setAddress(ByteString.copyFrom(encApi.hexDec(reqAddress)))
                    .setAmount(ByteString.copyFrom(ByteUtil.bigIntegerToBytes(reqAmount.toBigInteger())))
                    .setCryptoToken(cryptoToken)
                    .setSymbol(StringUtils.isNotBlank(reqOutputImpl.getSymbol()) ? reqOutputImpl.getSymbol() : "");
            oBody.addOutputs(oOutput);
        }
    }


    /**
     * 校验请求参数（根据请求个性封装）
     *
     * @param txImpl
     * @param ret
     */
    protected abstract boolean verifyRequestData(Wallet.MultiTransactionImpl txImpl,
                                                 Wallet.RespCreateTransaction.Builder ret);

}
