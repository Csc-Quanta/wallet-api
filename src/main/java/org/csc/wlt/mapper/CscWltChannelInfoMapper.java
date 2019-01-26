package org.csc.wlt.mapper;

import java.util.List;

import org.csc.wlt.entity.CscWltChannelInfo;
import org.csc.wlt.model.CscWltChannelModel;

/**
 * 中城钱包渠道配置
 */
public interface CscWltChannelInfoMapper {

	/**
	 * 查询单条数据
	 * 
	 * @param cscWltChannelModel
	 * @return
	 */
	List<CscWltChannelInfo> selectByExample(CscWltChannelModel cscWltChannelModel);

}
