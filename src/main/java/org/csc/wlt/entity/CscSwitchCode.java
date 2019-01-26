package org.csc.wlt.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 
 * 
 * @author fjy
 * @email 
 * @date 2019-01-15 10:25:31
 */
@Data
public class CscSwitchCode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//其他系统错误码
	private String othCode;
	//其他系统错误码对应的错误信息
	private String othMessage;
	//渠道的id 0：用户中心，1：短信系统
	private String chanId;
	//对应本系统的错误码
	private String errCode;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//状态值 0：无效，1：有效
	private String status;
}
