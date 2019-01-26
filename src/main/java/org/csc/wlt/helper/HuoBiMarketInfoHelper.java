package org.csc.wlt.helper;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.iPojoBean;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscWltChannelInfo;
import org.csc.wlt.common.Constants;
import org.csc.wlt.model.CscWltChannelModel;
import org.csc.wlt.model.CscWltCoinRateModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询行情信息
 */
@iPojoBean
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "huoBiMarketInfoHelper")
@Slf4j
@Data
public class HuoBiMarketInfoHelper implements ActorService, MarketInfoHelper {
	@ActorRequire(name = "httpHelp")
	HttpHelp httpHelp;
	@ActorRequire(name = "daos")
	private Daos daos;

	// TODO
	/**
	 * 查询全部的行情信息
	 */
	@Override
	public List<CscWltCoinRateModel> queryMarketInfoHelper() {
		CscWltChannelModel cscWltChannelModel = new CscWltChannelModel();
		cscWltChannelModel.setChannelName(Constants.HUO_BI_EXCHANGE);
		cscWltChannelModel.setConfigKey(Constants.HUO_BI_EXCHANGE_URL);
		CscWltChannelInfo cscWltChannelInfo = (CscWltChannelInfo) daos.wltChannelInfoDao
				.selectOneByExample(cscWltChannelModel);
		if (cscWltChannelInfo == null) {
			log.error("未配置火币交易url地址");
			return null;
		}
		String url = cscWltChannelInfo.getConfigVal() + Constants.HUO_BI_MARKET_URL;
		Map<String, String> headMap = new HashMap<>(1);
		headMap.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
		try {
			String result = httpHelp.get(url, headMap, true);
			Map<String, Object> mapType = JSON.parseObject(result, Map.class);
			List<Map> list = JSON.parseArray(JSON.toJSONString(mapType.get("data")), Map.class);
			List<CscWltCoinRateModel> cscWltCoinRateModels = new ArrayList<>();
			list.forEach(map -> {
				CscWltCoinRateModel cscWltCoinRateModel = new CscWltCoinRateModel();
				cscWltCoinRateModel.setSymbol(map.get("symbol").toString());
				cscWltCoinRateModel.setCoinRate(map.get("close").toString());
				cscWltCoinRateModels.add(cscWltCoinRateModel);
			});
			return cscWltCoinRateModels;
		} catch (Exception e) {
			log.error("获取火币连接失败，失败原因", e);
			throw new IllegalArgumentException("行情数据抓取错误");
		}
	}
}
