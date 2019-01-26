package org.csc.wlt.helper.validator;

import org.apache.commons.lang3.StringUtils;
import org.csc.wallet.service.Wallet.MultiTransactionBodyImpl;
import org.csc.wallet.service.Wallet.MultiTransactionImpl;
import org.csc.wallet.service.Wallet.MultiTransactionInputImpl;
import org.csc.wallet.service.Wallet.RespCreateTransaction;
import org.csc.wlt.enums.ReturnCodeEnum;

public class TxValidator {
	
	public void isVerify(MultiTransactionImpl reqTransaction, RespCreateTransaction.Builder ret) {
		
		if (reqTransaction.getTxBody() == null) {
			ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode());
			ret.setRplMsg("tx body is null");
		}

		MultiTransactionBodyImpl reqBodyImpl = reqTransaction.getTxBody();

		if (reqBodyImpl.getInputsList() == null || reqBodyImpl.getInputsList().isEmpty()) {
			ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode());
			ret.setRplMsg("inputs is null");
		}
		
		for(MultiTransactionInputImpl reqInputImpl : reqBodyImpl.getInputsList()) {
			if (StringUtils.isBlank(reqInputImpl.getAddress())) {
				ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode()).setRplMsg("input's address is null");
			}
		}
		
	}

}
