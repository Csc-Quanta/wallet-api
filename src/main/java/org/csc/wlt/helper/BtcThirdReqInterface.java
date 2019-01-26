//package org.csc.wlt.helper;
//
//import com.alibaba.fastjson.JSON;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import onight.osgi.annotation.NActorProvider;
//import onight.tfw.ntrans.api.ActorService;
//import onight.tfw.ntrans.api.annotation.ActorRequire;
//import org.apache.felix.ipojo.annotations.Instantiate;
//import org.apache.felix.ipojo.annotations.Provides;
//import org.csc.wlt.common.Constants;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@NActorProvider
//@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
//@Instantiate(name = "btcThirdReqInterface")
//@Slf4j
//@Data
//public class BtcThirdReqInterface implements ThirdReqInterface, ActorService {
//	@ActorRequire(name = "httpHelp")
//	HttpHelp httpHelp;
//
//	/**
//	 * 请求比特币官网地址，需要查询的地址list
//	 * 
//	 * @param url
//	 * @param address
//	 * @return
//	 */
//	@Override
//	public Map<String, Object> getAccountMoney(String url, List<String> address, String accountAddress) {
//		StringBuffer buffer = new StringBuffer(url).append(Constants.BTC_ACCOUNT_URL);
//		address.forEach(buffer::append);
//		buffer.delete(buffer.length() - 1, buffer.length());
//		String resString;
//		try {
//			resString = httpHelp.get(url, new HashMap<>(), true);
//		} catch (IOException e) {
//			log.warn("查询比特币余额失败", e);
//			return null;
//		}
//		List<Map> listMap = JSON.parseArray(resString, Map.class);
//		Map<String, Object> resultMap = new HashMap<>();
//		listMap.stream().map(map -> {
//			resultMap.put(map.get("address").toString(),
//					new BigDecimal(map.get("balance").toString()).divide(new BigDecimal(10).pow(8)));
//			return null;
//		});
//		return resultMap;
//	}
//
//	/**
//	 * @param accountAddress  账户地址 不能为空
//	 * @param contractAddress 合约地址 可以为空
//	 * @return
//	 */
//	@Override
//	public List<Map> queryTradeInfo(String accountAddress, String contractAddress) {
//		// https://chain.api.btc.com/v3/address/15urYnyeJe3gwbGJ74wcX89Tz7ZtsFDVew/tx?page=2
//
//		return null;
//	}
//}
