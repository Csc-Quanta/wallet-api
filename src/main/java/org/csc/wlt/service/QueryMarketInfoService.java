package org.csc.wlt.service;

import org.csc.wlt.model.CscMarketModel;

import java.util.Map;

/**
 * 查询虚拟币的兑换比率
 */
public interface QueryMarketInfoService {
	/**
	 * 根据主币类型查询
	 * 
	 * @param coinType
	 * @return
	 */
	CscMarketModel queryMarketInfo(String coinType);
}
