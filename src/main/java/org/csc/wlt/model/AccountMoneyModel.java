package org.csc.wlt.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取账户余额model
 */
@Data
@Builder
public class AccountMoneyModel implements Serializable {

	private String walletType;
	/**
	 * 账户地址
	 */
	private String accountAddress;
	/**
	 * 虚拟币的地址
	 */
	private List<String> coinAddress;
	/**
	 * 虚拟币的数量
	 */
	private String number;
	/**
	 * 节点ip
	 */
	private String stageIp;
}
