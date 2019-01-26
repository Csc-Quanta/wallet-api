//package org.csc.wlt.helper;
//
//import com.alibaba.fastjson.JSON;
//import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import onight.osgi.annotation.NActorProvider;
//import onight.tfw.ntrans.api.ActorService;
//import onight.tfw.ntrans.api.annotation.ActorRequire;
//import org.apache.commons.lang.StringUtils;
//import org.apache.felix.ipojo.annotations.Instantiate;
//import org.apache.felix.ipojo.annotations.Provides;
//import org.csc.wlt.dao.Daos;
//import org.csc.wlt.entity.CscWltChannelInfo;
//import org.csc.wlt.common.Constants;
//import org.csc.wlt.model.CoinTradeModel;
//import org.csc.wlt.model.CscWltChannelModel;
//import org.joda.time.DateTime;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.math.RoundingMode;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 以太坊相关接口
// */
//@NActorProvider
//@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
//@Instantiate(name = "ethThirdReqInterface")
//@Slf4j
//@Data
//public class EthThirdReqInterface implements ActorService, ThirdReqInterface {
//	@ActorRequire(name = "httpHelp")
//	HttpHelp httpHelp;
//	// 根据账户获取合约余额
//	final String ETH_GETSTORAGEAT = "eth_getStorageAt";
//	// 获取账户余额
//	final String ETH_GETBALANCE = "eth_getBalance";
//	final String RESULT = "result";
//	@ActorRequire(name = "daos")
//	private Daos daos;
//
//	/**
//	 * 获取账户余额
//	 * 
//	 * @param url
//	 * @param addressArray
//	 */
//	@Override
//	public Map<String, Object> getAccountMoney(String url, List<String> addressArray, String accountAddress) {
//		url = StringUtils.isNotBlank(url) ? url : "";
//		Map<String, Object> resultMap = new HashMap<>();
//		Object[] params = new Object[] { accountAddress, "latest" };
//		try {
//			Map<String, String> headerMap = new HashMap<>(1);
//			headerMap.put("Content-type", "application/json;charset=UTF-8");
//			JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(url));
//			String address = client.invoke(ETH_GETBALANCE, params, String.class, headerMap);
//			String result = new BigInteger(address.substring(2), 16).toString(10);
//			resultMap.put(accountAddress, new BigDecimal(result).divide(new BigDecimal(10).pow(18)));
//		} catch (Throwable throwable) {
//			log.warn("查询以太坊主账户余额失败", throwable);
//		}
//		// TODO
//		resultMap = new HashMap<>();
//		for (String address : addressArray) {
//			params = new Object[] { accountAddress, address, "latest" };
//			try {
//				Map<String, String> headerMap = new HashMap<>(1);
//				headerMap.put("Content-type", "application/json;charset=UTF-8");
//				JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(url));
//				String result = client.invoke(ETH_GETSTORAGEAT, params, String.class, headerMap);
//				result = new BigInteger(result.substring(2), 16).toString(10);
//				resultMap.put(address, new BigDecimal(result).divide(new BigDecimal(10).pow(18)));
//			} catch (Throwable throwable) {
//				log.warn("查询代币余额失败：", throwable);
//				continue;
//			}
//		}
//		return resultMap;
//	}
//
//	/**
//	 * 查询交易数据
//	 * 
//	 * @param accountAddress  账户地址 不能为空
//	 * @param contractAddress 合约地址 可以为空
//	 */
//	@Override
//	public List<Map> queryTradeInfo(String accountAddress, String contractAddress) {
//		// 查询eth的区块浏览器地址
//		CscWltChannelModel cscWltChannelModel = new CscWltChannelModel();
//		cscWltChannelModel.setChannelName(Constants.ETHEREUM);
//		cscWltChannelModel.setConfigKey(Constants.ETH_BLOCK_IE_URL);
//		CscWltChannelInfo cscWltChannelInfo = (CscWltChannelInfo) daos.wltChannelInfoDao
//				.selectOneByExample(cscWltChannelModel);
//		if (cscWltChannelInfo == null) {
//			log.error("未配置以太坊区块浏览器的生产url");
//			return null;
//		}
//		// 0x2c1ba59d6f58433fb1eaee7d20b26ed83bda51a3&startblock=0&endblock=2702578&page=1&offset=10&sort=asc&apikey=YourApiKeyToken
//		// api?module=account&action=tokentx&contractaddress=0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2&address=0x4e83362442b8d1bec281594cea3050c8eb01311c&page=1&offset=100&sort=asc&apikey=YourApiKeyToken
//		List<CoinTradeModel> resultList = new ArrayList<>();
//		// 如果合约地址为空
//		if (StringUtils.isEmpty(contractAddress)) {
//			int page = 0;
//			int pageSize = 100;
//			while (true) {
//				StringBuffer stringBuffer = new StringBuffer(cscWltChannelInfo.getConfigVal());
//				stringBuffer.append("/api?module=account&action=txlistinternal&address=").append(accountAddress)
//						.append("8&page=").append(page).append("&offset=").append(pageSize).append("&sort=asc");
//				String result;
//				try {
//					result = httpHelp.get(stringBuffer.toString(), new HashMap<>(), false);
//				} catch (IOException e) {
//					log.warn("查询以太坊主币交易数据失败,accountAddress:{},address:{}", accountAddress, contractAddress);
//					continue;
//				}
//				Map<String, Object> map = JSON.parseObject(result, Map.class);
//				List<Map> list = JSON.parseArray(JSON.toJSONString(map.get(RESULT)), Map.class);
//				if (list.isEmpty()) {
//					break;
//				}
//				list.forEach(resultMap -> {
//					CoinTradeModel coinTradeModel = new CoinTradeModel();
//					setterCoinTradeModel(coinTradeModel, resultMap, accountAddress);
//					resultList.add(coinTradeModel);
//				});
//				page++;
//			}
//		} else {
//			int page = 0;
//			int pageSize = 100;
//			while (true) {
//				StringBuffer stringBuffer = new StringBuffer(cscWltChannelInfo.getConfigVal())
//						.append("/api?module=account&action=tokentx&contractaddress=").append(contractAddress)
//						.append("&address=").append(accountAddress).append("&page=").append(page).append("&offset=")
//						.append(pageSize).append("&sort=asc");
//				String result;
//				try {
//					result = httpHelp.get(stringBuffer.toString(), new HashMap<>(), false);
//				} catch (IOException e) {
//					log.warn("查询以太坊代币交易数据失败,accountAddress:{},address:{}", accountAddress, contractAddress);
//					continue;
//				}
//				Map<String, Object> map = JSON.parseObject(result, Map.class);
//				List<Map> list = JSON.parseArray(JSON.toJSONString(map.get(RESULT)), Map.class);
//				if (list.isEmpty()) {
//					break;
//				}
//				list.forEach(resultMap -> {
//					CoinTradeModel coinTradeModel = new CoinTradeModel();
//					setterCoinTradeModel(coinTradeModel, resultMap, accountAddress);
//					resultList.add(coinTradeModel);
//				});
//				page++;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 设置他的属性
//	 * 
//	 * @param coinTradeModel
//	 * @param resultMap
//	 * @param accountAddress
//	 */
//	private void setterCoinTradeModel(CoinTradeModel coinTradeModel, Map resultMap, String accountAddress) {
//		coinTradeModel.setAmount(dealZero(resultMap.get("value").toString(), 15));
//		coinTradeModel.setHight(resultMap.get("blockNumber").toString());
//		// 0:进，1：出
//		coinTradeModel.setDirection(coinTradeModel.getReceiver().equals(accountAddress) ? "0" : "1");
//		coinTradeModel.setTransCost(dealZero(resultMap.get("gas").toString(), 15));
//		coinTradeModel.setSender(resultMap.get("from").toString());
//		coinTradeModel.setTradeHash(resultMap.get("hash").toString());
//		coinTradeModel.setReceiver(resultMap.get("to").toString());
//		coinTradeModel.setIsError(resultMap.get("isError").toString());
//		coinTradeModel.setErrCode(resultMap.get("errCode").toString());
//		coinTradeModel.setCreateTime(DateTime.parse(resultMap.get("timeStamp").toString()).toDate());
//	}
//
//	/**
//	 * 处理0
//	 * 
//	 * @param amount 需要处理的金额
//	 * @param scale  保留小数点多少位
//	 * @return
//	 */
//	private String dealZero(String amount, int scale) {
//		BigDecimal bigDecimal = new BigDecimal(amount);
//		amount = bigDecimal.divide(new BigDecimal(10).pow(18)).setScale(scale, RoundingMode.HALF_UP).toString();
//		while (amount.endsWith("0")) {
//			amount = amount.substring(0, amount.length() - 1);
//		}
//		return amount;
//	}
//	/*
//	 * public static void main(String[] args) { String method =
//	 * "eth_getTransactionCount"; Map<String,Object> resultMap = new HashMap<>();
//	 * Object[] params = new
//	 * Object[]{"0xd7C8d126252DD315b378B8DcBCc87ee7c4bE660a","latest"}; try {
//	 * Map<String,String> headerMap = new HashMap<>(1);
//	 * headerMap.put("Content-type","application/json;charset=UTF-8");
//	 * JsonRpcHttpClient client = new JsonRpcHttpClient(new
//	 * URL("http://47.94.105.234:8566")); String address = client.invoke(method,
//	 * params, String.class,headerMap); // String result = new
//	 * BigInteger(address.substring(2),16).toString(10);
//	 * System.out.println(address); } catch (Throwable throwable) {
//	 * log.warn("查询以太坊主账户余额失败",throwable); } }
//	 */
//	/*
//	 * public static void main(String[] args) { String amount = "0.123456789000000";
//	 * while (amount.endsWith("0")){ amount = amount.substring(0,amount.length()-1);
//	 * } System.out.println(amount); }
//	 */
//
//}
