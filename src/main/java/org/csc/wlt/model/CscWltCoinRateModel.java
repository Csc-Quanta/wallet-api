package org.csc.wlt.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.csc.wlt.entity.CscWltCoinRate;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CscWltCoinRateModel extends CscWltCoinRate {
	/**
	 * 币的简称list
	 */
	private List<String> coinSymbolList;
	// 根据简称模糊查询
	private String symbolLike;
}
