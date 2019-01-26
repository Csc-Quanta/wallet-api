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
//import org.csc.wlt.entity.CscWltCoinInfo;
//import org.csc.wlt.common.Constants;
//import org.csc.wlt.mapper.CscWltCoinInfoMapper;
//import org.csc.wlt.model.CscWltCoinModel;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 以太坊相关接口
// */
//@NActorProvider
//@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
//@Instantiate(name = "eosThirdReqInterface")
//@Slf4j
//@Data
//public class EosThirdReqInterface implements ActorService, ThirdReqInterface {
//	@ActorRequire(name = "httpHelp")
//	HttpHelp httpHelp;
//	@ActorRequire
//	private CscWltCoinInfoMapper cscWltCoinInfoMapper;
//
//	// 查询eos账户余额
//	// https://api.eospark.com
//	@Override
//	public Map<String, Object> getAccountMoney(String url, List<String> address, String accountAddress) {
//		url += Constants.EOS_ACCOUNT_URL;
//		StringBuffer stringBuffer = new StringBuffer(url).append("").append("&account=").append(accountAddress);
//		String resString;
//		try {
//			resString = httpHelp.get(stringBuffer.toString(), new HashMap<>(), false);
//		} catch (IOException e) {
//			log.warn("eos查询余额异常", e);
//			return null;
//		}
//		Map map = JSON.parseObject(resString, Map.class);
//		if (Integer.parseInt(map.get("errno").toString()) != 0) {
//			return null;
//		}
//		CscWltCoinModel cscWltCoinRateModel = new CscWltCoinModel();
//		cscWltCoinRateModel.setCoinAddressArray(address);
//		List<CscWltCoinInfo> cscWltCoinInfos = cscWltCoinInfoMapper.findByCondition(cscWltCoinRateModel);
//		List<Map> resultList = JSON.parseArray(
//				JSON.toJSONString(JSON.parseObject(JSON.toJSONString(map.get("data")), Map.class).get("symbol_list")),
//				Map.class);
//		Map<String, Object> resultMap = new HashMap<>();
//		resultList.forEach(maps -> {
//			String symbol = maps.get("symbol").toString();
//			cscWltCoinInfos.forEach(info -> {
//				if (info.getCoinSymbol().equals(symbol)) {
//					resultMap.put(info.getCoinAddress(), maps.get("balance"));
//				}
//			});
//
//		});
//		return resultMap;
//	}
//
//	@Override
//	public List<Map> queryTradeInfo(String accountAddress, String contractAddress) {
//		return null;
//	}
//}
