package org.csc.wlt.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.csc.wlt.entity.CscWltCoinInfo;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CscWltCoinModel extends CscWltCoinInfo {
	// 地址集合
	private List<String> coinAddressArray;
}
