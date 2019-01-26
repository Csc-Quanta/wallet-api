package org.csc.wlt.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import onight.tfw.ojpa.api.annotations.Tab;

@Tab(name = "csc_wlt_channel_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * 连接渠道配置表
 */
public class CscWltChannelInfo implements Serializable {

	private int autoId;
	/**
	 * 渠道简称
	 */
	private String channelName;
	/**
	 * 配置key
	 */
	private String configKey;

	/**
	 * 配置value
	 */
	private String configVal;

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
