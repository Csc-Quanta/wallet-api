package org.csc.wlt.helper.validator;

import org.apache.commons.lang3.StringUtils;
import org.csc.wallet.service.Wallet.MultiTransactionBodyImpl;
import org.csc.wallet.service.Wallet.MultiTransactionImpl;
import org.csc.wallet.service.Wallet.MultiTransactionInputImpl;
import org.csc.wallet.service.Wallet.MultiTransactionOutputImpl;
import org.csc.wallet.service.Wallet.RespCreateTransaction.Builder;
import org.csc.wlt.enums.ReturnCodeEnum;

public class TxSendCryptoValidator extends TxValidator {

	@Override
	public void isVerify(MultiTransactionImpl reqTransaction, Builder ret) {
		super.isVerify(reqTransaction, ret);
		
		if(ret.getRplCode()==ReturnCodeEnum.VALIDATION.getCode()) {
			return ;
		}
		
		MultiTransactionBodyImpl reqBodyImpl = reqTransaction.getTxBody();
		for(MultiTransactionInputImpl reqInputImpl : reqBodyImpl.getInputsList()) {
			if (StringUtils.isBlank(reqInputImpl.getSymbol())) {
				ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("symbol is null");
			}
			
			if (StringUtils.isBlank(reqInputImpl.getCryptoToken())) {
				ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("input CryptoToken is null");
			}
			
		}
		
		if(reqBodyImpl.getOutputsList().isEmpty()) {
			ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("OutputsList is empty");
		}
		
		for(MultiTransactionOutputImpl outputImpl : reqBodyImpl.getOutputsList()) {
			
			if (StringUtils.isBlank(outputImpl.getCryptoToken())) {
				ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("output CryptoToken is null");
			}
		}
		
	}
	

}
