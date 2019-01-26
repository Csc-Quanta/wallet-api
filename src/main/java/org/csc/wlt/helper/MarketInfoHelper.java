package org.csc.wlt.helper;

import org.csc.wlt.model.CscWltCoinRateModel;

import java.util.List;

public interface MarketInfoHelper {
	/**
	 * 获取行情信息
	 */
	List<CscWltCoinRateModel> queryMarketInfoHelper();
}
