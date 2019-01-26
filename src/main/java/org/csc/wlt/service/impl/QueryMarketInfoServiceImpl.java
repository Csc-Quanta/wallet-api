//package org.csc.wlt.service.impl;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import onight.osgi.annotation.NActorProvider;
//import onight.tfw.ntrans.api.ActorService;
//import onight.tfw.ntrans.api.annotation.ActorRequire;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang.time.DateUtils;
//import org.apache.felix.ipojo.annotations.Instantiate;
//import org.apache.felix.ipojo.annotations.Provides;
//import org.csc.wlt.dao.Daos;
//import org.csc.wlt.entity.CscWltCoinRate;
//import org.csc.wlt.enums.CoinSymbolEnum;
//import org.csc.wlt.common.Constants;
//import org.csc.wlt.model.CscMarketModel;
//import org.csc.wlt.model.CscWltCoinRateModel;
//import org.csc.wlt.service.QueryMarketInfoService;
//
//import java.util.*;
//
//@NActorProvider
//@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
//@Instantiate(name = "queryMarketInfoService")
//@Slf4j
//@Data
//public class QueryMarketInfoServiceImpl implements QueryMarketInfoService, ActorService {
//	@ActorRequire(name = "daos", scope = "global")
//	Daos daos;
//
//	/**
//	 * 根据主币类型查询
//	 * 
//	 * @param coinType
//	 * @return
//	 */
//	@Override
//	public CscMarketModel queryMarketInfo(String coinType) {
//		CoinSymbolEnum coinSymbolEnum = CoinSymbolEnum.getCoinSymbolByNumber(coinType);
//		if (coinSymbolEnum == null) {
//			log.info("不识别的钱包类型");
//			return null;
//		}
//		// 先查询主币
//		CscWltCoinRateModel cscWltCoinRateModel = new CscWltCoinRateModel();
//		cscWltCoinRateModel.setSymbol(coinSymbolEnum.name().toLowerCase().concat(Constants.USDT));
//		Date date12 = DateUtils.addHours(new Date(), -12);
//		cscWltCoinRateModel.setUpdateTime(date12);
//		List<CscWltCoinRate> cscWltCoinRates = (List) daos.wltCoinRateDao.selectByExample(cscWltCoinRateModel);
//		if (CollectionUtils.isEmpty(cscWltCoinRates)) {
//			log.info("主币的行情信息未配置或者同步时间已经少于12个小时");
//			return null;
//		}
//		CscWltCoinRate cscWltCoinRate = cscWltCoinRates.get(0);
//		// 查询其他和此币相关的数据
//		CscWltCoinRateModel childModel = new CscWltCoinRateModel();
//		childModel.setSymbolLike(coinSymbolEnum.name().toLowerCase());
//		childModel.setUpdateTime(date12);
//		List<CscWltCoinRate> childCoinRateList = (List) daos.wltCoinRateDao.selectByExample(childModel);
//		List<Map> resultList = new ArrayList<>();
//		childCoinRateList.forEach(childCoinRate -> {
//			Map map = new HashMap();
//			map.put("coinSymbol", childCoinRate.getSymbol());
//			map.put("coinRate", childCoinRate.getCoinRate());
//			resultList.add(map);
//		});
//		CscWltCoinRateModel rmbCoinRate = new CscWltCoinRateModel();
//		rmbCoinRate.setSymbol(CoinSymbolEnum.RMB.name().toLowerCase());
//		List<CscWltCoinRate> rmbCoinRateList = (List) daos.wltCoinRateDao.selectByExample(rmbCoinRate);
//		if (CollectionUtils.isEmpty(rmbCoinRateList)) {
//			log.error("未配置人民币和美金的兑换比率，请尽快配置");
//			return CscMarketModel.builder().mainRate(cscWltCoinRate.getCoinRate()).list(resultList).build();
//		} else {
//			return CscMarketModel.builder().mainRate(cscWltCoinRate.getCoinRate()).list(resultList)
//					.rmbRate(rmbCoinRateList.get(0).getCoinRate()).build();
//		}
//
//	}
//}
