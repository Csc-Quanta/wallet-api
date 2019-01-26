package org.csc.wlt.common;

public interface Constants {
	// usdt的symbol
	String USDT = "usdt";
	// 火币交易所
	String HUO_BI_EXCHANGE = "huo_bi_exchange";
	String EOS_ACCOUNT_URL = "/v1/chain/get_account";
	String BTC_ACCOUNT_URL = "/v3/address/";
	String ETH_ACCOUNT_URL = "/api?module=account&action=tokenbalance&contractaddress=";
	String HUO_BI_MARKET_URL = "/market/tickers";
	// 火币交易所url key
	String HUO_BI_EXCHANGE_URL = "HUO_BI_EXCHANGE_URL";
	// eth
	String ETHEREUM = "Ethereum";
	// 以太坊区块浏览器url
	String ETH_BLOCK_IE_URL = "ETH_BLOCK_IE_URL";
	//请求当前系统需要用到的头关键字
	String X_APP_AUTH = "X-APP-AUTH";
	//请求当前系统需要用到的头关键字
	String GRANT_TOKEN = "GRANT-TOKEN";
	//请求用户中心时用的头key
	String X_AUTHORIZATION = "X-AUTHORIZATION";
	//请求用户中心的serviceId
	String USER_SERVICE_ID = "SERVICE_ID";
	//用户中心的accessToken
	String USER_ACCESS_TOKEN = "ACCESS_TOKEN";
	//用户中心的GRANT-TOKEN
	String X_GRANT_TOKEN = "X-GRANT-TOKEN";
	//用户中心返回的成功码
	String SUC_CODE = "0000";
	//用户中心url
	String USER_CENTER_URL = "USER_CENTER_URL";
	//短信发送服务的url
	String MSG_SERVER_SEND_URL = "MSG_SERVER_SEND_URL";
	/**
	 * 1：已注册；2未注册
	 */
	String USER_EXIST_FLAG = "1";
	String USER_NOT_EXIST_FLAG = "2";
	//参数配置 第一组 短信配置
	String MSG_GROUP_ID = "1";
	/**
	 * 短信缓存
	 */
	String CACHE_SMS = "sms";
	/**
	 * 错误码缓存
	 */
	String CACHE_CODE = "err_code";
	/**
	 * 用户token相关
	 */
	String CACHE_USER_LOGIN = "user_login";
	/**
	 * 用户中心渠道
	 */
	String USER_CENTER_CHAN = "0";
	/**
	 * 短信服务
	 */
	String MESSAGE_SERVER_CHAN = "1";

}
