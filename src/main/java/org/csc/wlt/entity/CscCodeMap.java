package org.csc.wlt.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author fjy
 * @email 
 * @date 2019-01-14 20:07:35
 */
@Data
public class CscCodeMap implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//错误码
	private String errCode;
	//错误信息
	private String errMessage;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//状态 0：失效，1：有效
	private String status;
}
