package org.csc.wlt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * 钱包支持的代币
 */
public class CscWltCoinInfo implements Serializable {

	private int autoId;
	/**
	 * 币的简称
	 */
	private String coinSymbol;
	/**
	 * 币的名称
	 */
	private String coinName;
	/**
	 * 币的地址
	 */
	private String coinAddress;

	/**
	 * 币的头像 url
	 */
	private String coinImg;

	/**
	 * 无主账户用0表示 dgd的主账户是eth
	 */
	private String coinMain;

	/**
	 * 0：不是，1：是 默认值：0
	 */
	private String coinGroom;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 币的地址
	 */
	private Date createTime;

	/**
	 * 币的地址
	 */
	private Date updateTime;

	/**
	 * 1-新增；2-修改；3-删除
	 */
	private String dmlFlag;
	/**
	 * 创建人
	 */
	private String creater;
	/**
	 * 更新人
	 */
	private String updator;
}
