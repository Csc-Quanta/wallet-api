package org.csc.wlt.helper.validator;

import org.apache.commons.lang3.StringUtils;
import org.csc.wallet.service.Wallet.MultiTransactionBodyImpl;
import org.csc.wallet.service.Wallet.MultiTransactionImpl;
import org.csc.wallet.service.Wallet.MultiTransactionInputImpl;
import org.csc.wallet.service.Wallet.RespCreateTransaction.Builder;
import org.csc.wlt.enums.ReturnCodeEnum;

public class TxCreateCryptoValidator extends TxValidator {

	@Override
	public void isVerify(MultiTransactionImpl reqTransaction, Builder ret) {
		super.isVerify(reqTransaction, ret);
		
		if(ret.getRplCode()==ReturnCodeEnum.VALIDATION.getCode()) {
			return ;
		}
		
		MultiTransactionBodyImpl reqBodyImpl = reqTransaction.getTxBody();
		
		if(StringUtils.isBlank(reqBodyImpl.getData()) && reqBodyImpl.getCryptoTokenData()==null) {
			ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("CryptoTokenData is null");
		}
		
	}
	

}
