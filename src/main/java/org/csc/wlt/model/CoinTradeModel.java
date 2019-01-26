package org.csc.wlt.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
/**
 * 虚拟货币的交易记录
 */
public class CoinTradeModel implements Serializable {
	// 交易hash
	private String tradeHash;
	// 块的高度
	private String hight;
	// 发送者
	private String sender;
	// 接收者
	private String receiver;
	// 进。出
	private String direction;
	// 交易额
	private String amount;
	// 交易手续费
	private String transCost;
	// 是否有异常
	private String isError;
	// 错误码
	private String errCode;
	// 创建时间
	private Date createTime;
}
