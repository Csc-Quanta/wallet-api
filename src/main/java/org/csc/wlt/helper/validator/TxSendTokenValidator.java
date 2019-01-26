package org.csc.wlt.helper.validator;

import org.apache.commons.lang3.StringUtils;
import org.csc.wallet.service.Wallet.MultiTransactionBodyImpl;
import org.csc.wallet.service.Wallet.MultiTransactionImpl;
import org.csc.wallet.service.Wallet.MultiTransactionInputImpl;
import org.csc.wallet.service.Wallet.MultiTransactionOutputImpl;
import org.csc.wallet.service.Wallet.RespCreateTransaction.Builder;
import org.csc.wlt.enums.ReturnCodeEnum;

public class TxSendTokenValidator extends TxValidator {

	@Override
	public void isVerify(MultiTransactionImpl reqTransaction, Builder ret) {
		super.isVerify(reqTransaction, ret);
		
		if(ret.getRplCode()==ReturnCodeEnum.VALIDATION.getCode()) {
			return ;
		}
		
		MultiTransactionBodyImpl reqBodyImpl = reqTransaction.getTxBody();
		for(MultiTransactionInputImpl reqInputImpl : reqBodyImpl.getInputsList()) {
			if (StringUtils.isBlank(reqInputImpl.getToken())) {
				ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("token is null");
			}
			
			if (StringUtils.isBlank(reqInputImpl.getAmount())) {
				ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("input amount is null");
			}
			
		}
		
		for(MultiTransactionOutputImpl outputImpl : reqBodyImpl.getOutputsList()) {
			
			if (StringUtils.isBlank(outputImpl.getAmount())) {
				ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("output amount is null");
			}
			
		}
		
	}
	

}
