package org.csc.wlt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 行情信息同步表
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CscWltCoinRate implements Serializable {

	private int autoId;
	/**
	 * 币的简称
	 */
	private String symbol;

	/**
	 * 币的简称
	 */
	private String coinRate;
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
