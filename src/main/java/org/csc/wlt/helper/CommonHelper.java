package org.csc.wlt.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;
import org.csc.wallet.service.Wallet;
import org.csc.wallet.service.Wallet.BaseData;
import org.csc.wlt.common.Constants;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscWltParameter;
import org.csc.wlt.entity.CscWltParameterExample;
import org.csc.wlt.enums.NodeInterfaceEnum;
import org.csc.wlt.enums.ReturnCodeEnum;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.iPojoBean;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.IPacketSender;
import onight.tfw.otransio.api.beans.FramePacket;
import org.csc.wlt.model.AppReqHeader;

/**
 * @author jack
 * 
 *         address 账户相关信息获取
 * 
 */
@iPojoBean
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "commHelper")
@Slf4j
@Data
public class CommonHelper implements ActorService {

	@ActorRequire(name = "daos", scope = "global")
	Daos daos;

	@ActorRequire(name = "http", scope = "global")
	IPacketSender sender;

	public Map<String, String> settingMap = new HashMap<String, String>();

	private static HashMap<String, Integer> nodeCount = new HashMap<>();

	/**
	 * 检查头参数
	 * @param packet
	 * @return
	 */
	public AppReqHeader getHeader(FramePacket packet){
		AppReqHeader appReqHeader = JSON.parseObject(packet.getHttpServerletRequest().getHeader(Constants.X_APP_AUTH),
				AppReqHeader.class);
		appReqHeader.setGrantToken(packet.getHttpServerletRequest().getHeader(Constants.GRANT_TOKEN));
		log.info("Json.toJsonString(appReqHeader):{}", JSON.toJSONString(appReqHeader));
		return appReqHeader;
	}

	/**
	 * 校验参数
	 * @param packet
	 * @param pb
	 * @param ret
	 * @return
	 */
	public String[] checkParam(FramePacket packet, Wallet.BaseData pb, ResultOuterClass.Result.Builder ret) {
		AppReqHeader header = getHeader(packet);
		String[] strings =new String[2];
		if (header== null || !header.checkParam()
				|| (strings = getDecryptData(pb, header.getBusi()))[0] !=null) {
			strings[0] = ReturnCodeEnum.VALIDATION.getMsg();
			ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode());
			ret.setRplMsg("param is error");
		}
		return strings;
	}



	public FramePacket send(FramePacket packet) {
		if (packet.getExtHead().get("_url").toString().contains("txt/pbmtx.do")
				&& !nodeCount.keySet().contains(packet.getExtHead().get("_url"))) {
			nodeCount.put((String) packet.getExtHead().get("_url"), 0);
		}

		CscWltParameterExample parameterExample = new CscWltParameterExample();
		parameterExample.createCriteria().andParamCodeEqualTo("time_out_second");
		Object o = daos.wltParameterDao.selectOneByExample(parameterExample);

		int timeout = 5000;

		if (o != null) {
			CscWltParameter p = (CscWltParameter) o;
			timeout = Integer.parseInt(p.getParamValue());
		}
		FramePacket ret = sender.send(packet, timeout);
		if (ret == null || ret.getBody() == null) {

			log.warn(
					"\n timeout " + timeout + " and try again after 20 seconds==>\n" + packet.getExtHead().get("_url"));
			try {
				Thread.currentThread().sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (o != null) {
				CscWltParameter p = daos.wltParameterDao.selectByPrimaryKey((CscWltParameter) o);
				timeout = Integer.parseInt(p.getParamValue());
			}

			ret = sender.send(packet, timeout);
		}
		if (packet.getExtHead().get("_url").toString().contains("/txt/pbmtx.do"))
			nodeCount.put((String) packet.getExtHead().get("_url"),
					nodeCount.get((String) packet.getExtHead().get("_url")) + 1);

		StringBuffer logStr = new StringBuffer("\n");
		for (String s : nodeCount.keySet()) {

			logStr.append(s).append("==>").append(nodeCount.get(s)).append("\n");
		}
		log.info(logStr.toString());
		return ret;
	}

	public CscWltParameter getParamEntity(String key) {
		CscWltParameterExample example = new CscWltParameterExample();
		example.createCriteria().andParamCodeEqualTo(key);
		Object o = daos.wltParameterDao.selectOneByExample(example);
		return o == null ? null : (CscWltParameter) o;
	}

	public void updateParamValue(String key, String value) {
		CscWltParameterExample example = new CscWltParameterExample();
		example.createCriteria().andParamCodeEqualTo(key);
		CscWltParameter param = new CscWltParameter();
		param.setParamValue(value);
		daos.wltParameterDao.updateByExampleSelective(param, example);
	}

	/**
	 * 根据parentKey获取字典数组
	 * 
	 * @param parentKey
	 * @return
	 */
	public List<Object> getParamEntitiesByKey(String parentKey) {
		CscWltParameterExample example = new CscWltParameterExample();
		example.createCriteria().andParamCodeEqualTo(parentKey);
		return daos.wltParameterDao.selectByExample(example);
	}

	/**
	 * 获取配置字段值
	 * 
	 * @param key
	 * @return
	 */
	public String getParamValue(String key) {

		if (settingMap != null && settingMap.get(key) != null) {
			return settingMap.get(key);
		} else if (settingMap == null) {
			initSettingMap();
		}

		CscWltParameter o = getParamEntity(key);
		return o == null ? null : o.getParamValue();
	}

	private synchronized void initSettingMap() {
		List<Object> list = daos.wltParameterDao.findAll(new ArrayList<>());
		for (Object o : list) {
			CscWltParameter param = (CscWltParameter) o;
			settingMap.put(param.getParamCode(), param.getParamValue());
		}
	}

	public String[] getDecryptData(BaseData pb, String busi) {
		String[] str = new String[2];
		if (pb == null || StringUtils.isAnyBlank(busi)) {
			str[0] = ReturnCodeEnum.VALIDATION.getMsg();
			return str;
		}
		String key = getParamValue(busi);
		if (key == null) {
			str[0] = String.format("the key of busi[%s] is null ", pb.getBusi());
			return str;
		}else if(key.equals("UNSIGNED")){
			str[1] = pb.getData();
		}else {
//			str[1] = SM4Utils.getDecStrECB(pb.getData(), key);
	
		}
		return str;
	}

	/**
	 * 获取节点集合
	 * 
	 * @return
	 */
	public String[] getNodeList() {
		String nodes = getParamValue(NodeInterfaceEnum.NODE_URL_LIST.getValue());
		if (nodes == null) {
			log.error("node is null");
			return null;
		} else {
			return nodes.split(",");
		}
	}

}
