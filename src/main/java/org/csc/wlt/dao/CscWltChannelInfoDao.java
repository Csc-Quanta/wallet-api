package org.csc.wlt.dao;

import java.util.List;

import org.csc.wlt.entity.CscKey;
import org.csc.wlt.entity.CscWltChannelInfo;
import org.csc.wlt.mapper.CscWltChannelInfoMapper;
import org.csc.wlt.model.CscWltChannelModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "CSC_WLT_CHANNEL_INFO")
public class CscWltChannelInfoDao extends ExtendDaoSupper<CscWltChannelInfo, CscWltChannelModel, CscKey> {
	private CscWltChannelInfoMapper mapper;

	@Override
	public List<CscWltChannelInfo> selectByExample(CscWltChannelModel example) {
		return mapper.selectByExample(example);

	}

	@Override
	public CscWltChannelInfo selectOneByExample(CscWltChannelModel example) {
		List<CscWltChannelInfo> list = mapper.selectByExample(example);
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new RuntimeException("many result find " + list.size());
		}
		return list.get(0);
	}
}
