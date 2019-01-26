package org.csc.wlt.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 
 * 
 * @author fjy
 * @email 
 * @date 2019-01-24 18:24:47
 */
@Data
public class CscWltAddress implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String addressId;
	//
	private String cUserId;
	//账户地址
	private String address;
	//
	private String type;
	//公钥
	private String publicKey;
	//私钥
	private String privateKey;
	//交易次数
	private Integer nonce;
	//账户余额
	private Long balance;
	//种子信息
	private String seed;
	//公私钥对的唯一标识
	private String bcuid;
	//
	private Date createdTime;
	//
	private Date updatedTime;
	//
	private String reserved1;
	//
	private String reserved2;
}
