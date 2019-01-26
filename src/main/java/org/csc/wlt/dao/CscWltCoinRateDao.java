package org.csc.wlt.dao;

import java.util.List;

import org.csc.wlt.entity.CscKey;
import org.csc.wlt.entity.CscWltCoinRate;
import org.csc.wlt.mapper.CscWltCoinRateMapper;
import org.csc.wlt.model.CscWltCoinRateModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "CSC_WLT_COIN_RATE")
public class CscWltCoinRateDao extends ExtendDaoSupper<CscWltCoinRate, CscWltCoinRateModel, CscKey> {

	private CscWltCoinRateMapper mapper;

	@Override
	public List<CscWltCoinRate> findAll(List<CscWltCoinRate> records) {
		return mapper.queryRate(records);
	}

	@Override
	public int batchUpdate(List<CscWltCoinRate> records) {
		return mapper.batchUpdate(records);
	}

	@Override
	public List<CscWltCoinRate> selectByExample(CscWltCoinRateModel example) {
		return mapper.queryBySymbol(example);
	}
}
