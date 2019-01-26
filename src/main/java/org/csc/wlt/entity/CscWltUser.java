package org.csc.wlt.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 
 * 
 * @author fjy
 * @email 
 * @date 2019-01-11 10:01:51
 */
@Data
public class CscWltUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增长主键
	private Long autoId;
	//应用号
	private String appId;
	//钱包类型 1：CSCQ,2：BTC,3：ETH,4：EOS,5：CWV
	private String wltType;
	//用户中心的c_user_id
	private String cUserId;
	//钱包名称
	private String wltName;
	//创建类型 1：创建，2：导入
	private String createType;
	//钱包协议 3：44协议
	private String wltProtocol;
	//钱包状态 0：无效，1：有效
	private String status;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
}
