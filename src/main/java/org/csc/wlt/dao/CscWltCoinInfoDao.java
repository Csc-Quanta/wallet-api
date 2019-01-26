package org.csc.wlt.dao;

import java.util.List;

import org.csc.wlt.entity.CscKey;
import org.csc.wlt.entity.CscWltCoinInfo;
import org.csc.wlt.mapper.CscWltCoinInfoMapper;
import org.csc.wlt.model.CscWltCoinModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;

@Tab(name = "CSC_WLT_COIN_INFO")
@Data
@EqualsAndHashCode(callSuper = false)
public class CscWltCoinInfoDao extends ExtendDaoSupper<CscWltCoinInfo, CscWltCoinModel, CscKey> {
	private CscWltCoinInfoMapper mapper;

	public List<CscWltCoinInfo> selectByExample(CscWltCoinModel cscWltCoinModel) {
		return mapper.findByCondition(cscWltCoinModel);
	}
}
