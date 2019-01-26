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
public class CscWltAppParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer autoId;
	//应用id
	private String appId;
	//分组id 0:短信配置 信息
	private String groupId;
	//属性id
	private String propertyId;
	//属性值
	private String propertyName;
	//配置状态 0：无效，1：有效
	private String status;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
}
