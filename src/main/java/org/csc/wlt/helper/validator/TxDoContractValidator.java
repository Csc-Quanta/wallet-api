package org.csc.wlt.helper.validator;

import org.apache.commons.lang3.StringUtils;
import org.csc.wallet.service.Wallet.MultiTransactionBodyImpl;
import org.csc.wallet.service.Wallet.MultiTransactionImpl;
import org.csc.wallet.service.Wallet.RespCreateTransaction.Builder;
import org.csc.wlt.enums.ReturnCodeEnum;

public class TxDoContractValidator extends TxValidator {

	@Override
	public void isVerify(MultiTransactionImpl reqTransaction, Builder ret) {
		super.isVerify(reqTransaction, ret);
		
		if(ret.getRplCode()==ReturnCodeEnum.VALIDATION.getCode()) {
			return ;
		}
		
		MultiTransactionBodyImpl reqBodyImpl = reqTransaction.getTxBody();
		if(StringUtils.isBlank(reqBodyImpl.getData())) {
			ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("data is null");
		}
		
	}
	

}
