package org.csc.wlt.helper;

import com.googlecode.protobuf.format.JsonFormat.ParseException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.beans.FramePacket;
import onight.tfw.outils.serialize.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.bcapi.EncAPI;
import org.csc.bcapi.KeyPairs;
import org.csc.wallet.service.Wallet.*;
import org.csc.wlt.common.CaCheConstants;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscWltAddress;
import org.csc.wlt.entity.CscWltAddressModel;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.model.AppReqHeader;
import org.csc.wlt.utils.JsonUtil;
import org.csc.wlt.utils.UserCacheUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jack
 * 
 *         address 账户相关信息获取
 * 
 */
@NActorProvider
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "addrHelper")
@Slf4j
@Data
public class AddressHelper implements ActorService {

	@ActorRequire(name = "bc_encoder", scope = "global")
	EncAPI encApi;

	@ActorRequire(name = "commHelper", scope = "global")
	CommonHelper commonHelper;

	@ActorRequire(name = "userCacheUtil", scope = "global")
	UserCacheUtil userCacheUtil;

	@ActorRequire(name = "Account_Impl", scope = "global")
	AccountImpl accountImpl;

	@ActorRequire(name = "daos", scope = "global")
	Daos daos;

	BigDecimal ws = new BigDecimal("1000000000000000000");

//	private static String QUERY_ADDRESS = "http://127.0.0.1:8000/fbs/act/pbgac.do";
	static {
//		QUERY_ADDRESS = props.get("query_address", "http://127.0.0.1:8000/fbs/act/pbgac.do");
	}

	/**
	 * 生成地址并入库
	 * 
	 * @return
	 */
	public void registerAddress(ReqNewAddress.Builder req, RetNewAddress.Builder ret, String userId) {
		KeyPairs key = encApi.genKeys();
		if (StringUtils.isNotBlank(req.getSeed())) {
			key = encApi.genKeys(req.getSeed());
		}

		// 写库操作
		Date now = new Date();
		CscWltAddress addressEntity = new CscWltAddress();
		addressEntity.setAddressId(UUIDGenerator.generate());
		addressEntity.setAddress(key.getAddress());
		addressEntity.setBalance(0L);
		addressEntity.setBcuid(key.getBcuid());
		addressEntity.setCreatedTime(now);
		addressEntity.setNonce(0);
		addressEntity.setPrivateKey(key.getPrikey());
		addressEntity.setCUserId(userId);
		addressEntity.setPublicKey(key.getPubkey());
		addressEntity.setReserved1("");
		addressEntity.setReserved2("");
		addressEntity.setSeed(req.getSeed());
		addressEntity.setType("");
		addressEntity.setUpdatedTime(now);

		daos.wltAddressDao.insert(addressEntity);

		ret.setRplCode(ReturnCodeEnum.DONE.getCode());
		ret.setAddr(key.getAddress());
	}

	public void getAccountInfo(final FramePacket pack, final BaseData pb, RespGetAccount.Builder ret)
			throws ParseException {
		String[] str = commonHelper.getDecryptData(pb, pb.getBusi());
		if (str[0] != null) {
//			ret.setRplMsg(str[0]);
			return;
		}

		ReqGetAccount.Builder accountEntity = ReqGetAccount.newBuilder();
		JsonUtil.jsonToObject(str[1], accountEntity);
		accountImpl.getAccountInfo(accountEntity.build(), ret);
		
	}

	public void registerAddress(FramePacket pack, BaseData pb, RetNewAddress.Builder ret) throws ParseException {
		String[] str = commonHelper.getDecryptData(pb, pb.getBusi());
		if (str[0] != null) {
			ret.setMsg(str[0]);
			return;
		}

		ReqNewAddress.Builder reqNewAddress = ReqNewAddress.newBuilder();
		JsonUtil.jsonToObject(str[1], reqNewAddress);
		AppReqHeader appReqHeader = commonHelper.getHeader(pack);
		String userId = userCacheUtil.get(CaCheConstants.USER_TOKEN_PRX,appReqHeader.getGrantToken());
		CscWltAddressModel model = new CscWltAddressModel();
		model.setCUserId(userId);
		CscWltAddress cscWltAddress = (CscWltAddress) daos.wltAddressDao.selectOneByExample(model);
		if(cscWltAddress == null){
			registerAddress(reqNewAddress, ret,userId );
		}else {
			ret.setAddr(cscWltAddress.getAddress());
		}
		ret.setRplCode(ReturnCodeEnum.DONE.getCode());
	}
}
