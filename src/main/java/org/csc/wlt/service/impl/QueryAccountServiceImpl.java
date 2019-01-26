//package org.csc.wlt.service.impl;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import onight.osgi.annotation.NActorProvider;
//import onight.tfw.ntrans.api.ActorService;
//import onight.tfw.ntrans.api.annotation.ActorRequire;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.felix.ipojo.annotations.Instantiate;
//import org.apache.felix.ipojo.annotations.Provides;
//import org.csc.wlt.entity.CscWltCoinInfo;
//import org.csc.wlt.entity.CscWltCoinRate;
//import org.csc.wlt.enums.CoinSymbolEnum;
//import org.csc.wlt.common.Constants;
//import org.csc.wlt.mapper.CscWltCoinInfoMapper;
//import org.csc.wlt.mapper.CscWltCoinRateMapper;
//import org.csc.wlt.model.AccountMoneyModel;
//import org.csc.wlt.model.CscWltCoinModel;
//import org.csc.wlt.service.QueryAccountService;
//import org.csc.wlt.utils.ThirdReqInterfaceUtil;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@NActorProvider
//@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
//@Instantiate(name = "queryAccountService")
//@Slf4j
//@Data
//public class QueryAccountServiceImpl implements QueryAccountService, ActorService {
//	@ActorRequire
//	private CscWltCoinInfoMapper cscWltCoinInfoMapper;
//	@ActorRequire(name = "thirdReqInterfaceUtil", scope = "global")
//	ThirdReqInterfaceUtil thirdReqInterfaceUtil;
//	@ActorRequire
//	private CscWltCoinRateMapper cscWltCoinRateMapper;
//
//	/**
//	 * 获取钱包账户余额
//	 * 
//	 * @param accountMoneyModel 查询信息
//	 */
//	@Override
//	public Map<String, Object> getAccountMoney(AccountMoneyModel accountMoneyModel) {
//		CoinSymbolEnum coinSymbolEnum = CoinSymbolEnum.getCoinSymbolByNumber(accountMoneyModel.getWalletType());
//		if (coinSymbolEnum == null) {
//			// 不认识的钱包
//			return null;
//		}
//		Map<String, Object> queryMap = thirdReqInterfaceUtil.getInterfaceMap().get(coinSymbolEnum.name())
//				.getAccountMoney(accountMoneyModel.getStageIp(), accountMoneyModel.getCoinAddress(),
//						accountMoneyModel.getAccountAddress());
//		// 已经查询出来余额
//		if (queryMap == null || queryMap.isEmpty()) {
//			return null;
//		}
//		CscWltCoinModel cscWltCoinModel = new CscWltCoinModel();
//		cscWltCoinModel.setCoinMain(coinSymbolEnum.name().toLowerCase());
//		cscWltCoinModel.setCoinAddressArray(accountMoneyModel.getCoinAddress());
//		// 查询币信息
//		List<CscWltCoinInfo> list = cscWltCoinInfoMapper.findByCondition(cscWltCoinModel);
//		List<Map<String, String>> resultList = new ArrayList<>();
//		List<CscWltCoinRate> symbolList = new ArrayList<>();
//		list.forEach(s -> {
//			CscWltCoinRate cscWltCoinRate = new CscWltCoinRate();
//			Map<String, String> tempMap = new HashMap<>();
//			tempMap.put("coinSymbol", s.getCoinSymbol());
//			tempMap.put("coinName", s.getCoinName());
//			tempMap.put("coinAddress", s.getCoinAddress());
//			tempMap.put("coinImage", s.getCoinImg());
//			tempMap.put("coinNumber", queryMap.get(s.getCoinAddress()).toString());
//			resultList.add(tempMap);
//			// 说明这个账号是主账户
//			if (s.getCoinMain().equals("0")) {
//				cscWltCoinRate.setSymbol(s.getCoinSymbol().concat(Constants.USDT));
//			} else {
//				cscWltCoinRate.setSymbol(s.getCoinSymbol().concat(s.getCoinMain()));
//			}
//			symbolList.add(cscWltCoinRate);
//		});
//		// 查询兑换比率
//		List<CscWltCoinRate> rateList = cscWltCoinRateMapper.queryRate(symbolList);
//		// 先把主币查询出来
//		CscWltCoinRate firstCscWltCoinRate = CollectionUtils.select(rateList,
//				cscWltCoinRate -> cscWltCoinRate.getSymbol().equals(coinSymbolEnum.name().concat(Constants.USDT)))
//				.stream().findFirst().get();
//		BigDecimal firstCoinRate = new BigDecimal(firstCscWltCoinRate.getCoinRate());
//		double totalDollar = 0;
//		for (Map<String, String> map : resultList) {
//			if (map.get("coinSymbol").concat(Constants.USDT).equals(firstCscWltCoinRate.getSymbol())) {
//				// 这个是主账户的
//				BigDecimal coinDollar = new BigDecimal(map.get("coinNumber")).multiply(firstCoinRate).setScale(2);
//				totalDollar += coinDollar.doubleValue();
//				map.put("coinRmb", coinDollar.toString());
//			} else {
//				for (CscWltCoinRate s : rateList) {
//					if (map.get("coinSymbol").concat(coinSymbolEnum.name()).equals(s.getSymbol())) {
//						// 当前币的数量
//						// 获得eth的数量
//						BigDecimal coinDollar = new BigDecimal(map.get("coinNumber"))
//								.multiply(new BigDecimal(s.getCoinRate())).multiply(firstCoinRate).setScale(2);
//						totalDollar += coinDollar.doubleValue();
//						map.put("coinRmb", coinDollar.toString());
//					}
//				}
//			}
//		}
//		Map<String, Object> resultMap = new HashMap<>();
//		resultMap.put("totalRmbr", String.valueOf(totalDollar));
//		resultMap.put("list", resultList);
//		return resultMap;
//	}
//}
