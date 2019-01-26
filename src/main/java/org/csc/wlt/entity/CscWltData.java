package org.csc.wlt.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 
 * 
 * @author fjy
 * @email 
 * @date 2019-01-09 16:16:16
 */
@Data
public class CscWltData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long autoId;
	//钱包和用户的关系id
	private Long wltUserId;
	//钱包对应的数据
	private String data;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//数据状态 0：无效，1：有效
	private String status;
}
