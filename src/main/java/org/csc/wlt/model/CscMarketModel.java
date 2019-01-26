package org.csc.wlt.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 中城行情信息
 */
@Data
@Builder
public class CscMarketModel implements Serializable {
	// 人民币的汇率
	private String rmbRate;
	// 主账户的汇率
	private String mainRate;
	// 子账户兑主账户的汇率
	private List<Map> list;
}
