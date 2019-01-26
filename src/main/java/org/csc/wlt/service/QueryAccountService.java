package org.csc.wlt.service;

import org.csc.wlt.model.AccountMoneyModel;

import java.util.Map;

/**
 * 查询账户余额的方法
 */
public interface QueryAccountService {
	/**
	 * 获取钱包账户余额
	 * 
	 * @param accountMoneyModel 查询信息
	 */
	Map<String, Object> getAccountMoney(AccountMoneyModel accountMoneyModel);

}
