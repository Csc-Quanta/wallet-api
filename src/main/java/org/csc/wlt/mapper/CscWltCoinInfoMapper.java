package org.csc.wlt.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.csc.wlt.entity.CscWltCoinInfo;
import org.csc.wlt.model.CscWltCoinModel;

import java.util.List;

public interface CscWltCoinInfoMapper {
	/**
	 *
	 * @param cscWltCoinModel
	 * @return
	 */
	List<CscWltCoinInfo> findByCondition(CscWltCoinModel cscWltCoinModel);
}
