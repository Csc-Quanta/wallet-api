package org.csc.wlt.mapper;

import org.apache.ibatis.annotations.Param;
import org.csc.wlt.entity.CscWltCoinRate;
import org.csc.wlt.model.CscWltCoinRateModel;

import java.util.List;

/**
 * 行情信息同步表
 */
public interface CscWltCoinRateMapper {
	/**
	 * 批量更新
	 * 
	 * @param list
	 */
	int batchUpdate(@Param("list") List<CscWltCoinRate> list);

	/**
	 * 批量查询兑换比率
	 * 
	 * @return
	 */
	List<CscWltCoinRate> queryRate(@Param("list") List<CscWltCoinRate> list);

	/**
	 * 根据检查查询
	 * 
	 * @param cscWltCoinRateModel
	 * @return
	 */
	List<CscWltCoinRate> queryBySymbol(CscWltCoinRateModel cscWltCoinRateModel);

}
