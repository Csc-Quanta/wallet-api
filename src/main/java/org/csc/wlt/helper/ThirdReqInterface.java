package org.csc.wlt.helper;

import java.util.List;
import java.util.Map;

/**
 * 请求第三方接口
 */
public interface ThirdReqInterface {
	/**
	 * 获取账户余额
	 * 
	 * @param url     可为空，默认官方url
	 * @param address 查询地址
	 */
	Map<String, Object> getAccountMoney(String url, List<String> address, String accountAddress);

	/**
	 * 查询交易数据
	 * 
	 * @param accountAddress  账户地址 不能为空
	 * @param contractAddress 合约地址 可以为空
	 */
	List<Map> queryTradeInfo(String accountAddress, String contractAddress);
}
