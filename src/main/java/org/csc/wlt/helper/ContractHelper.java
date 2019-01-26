package org.csc.wlt.helper;

import com.googlecode.protobuf.format.JsonFormat.ParseException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.beans.FramePacket;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.bcapi.EncAPI;
import org.csc.bcvm.CodeBuild;
import org.csc.bcvm.call.CallTransaction;
import org.csc.wallet.service.Wallet.BaseData;
import org.csc.wallet.service.Wallet.ReqCompileContract;
import org.csc.wallet.service.Wallet.ReqCompileContract.Param;
import org.csc.wallet.service.Wallet.RespCompileContract;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.utils.JsonUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author jack
 * 
 *         address 账户相关信息获取
 * 
 */
@NActorProvider
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "contractHelper")
@Slf4j
@Data
public class ContractHelper implements ActorService {

	@ActorRequire(name = "bc_encoder", scope = "global")
	EncAPI encApi;

	@ActorRequire(name = "Transaction_Impl")
	TransactionImpl transactionImpl;

	@ActorRequire(name = "commHelper", scope = "global")
	CommonHelper commonHelper;

	BigDecimal ws = new BigDecimal("1000000000000000000");

	/**
	 * 编译合约源码
	 * 
	 * @return
	 */
	public void compileCode(FramePacket pack, BaseData pb, RespCompileContract.Builder ret) throws ParseException {

		String[] str = commonHelper.getDecryptData(pb, pb.getBusi());
		if (str[0] != null) {
			ret.setRplMsg(str[0]);
			return;
		}

		ReqCompileContract.Builder req = ReqCompileContract.newBuilder();
		JsonUtil.jsonToObject(str[1], req);
		//req.setCode(str[1]);
		if(StringUtils.isEmpty(req.getCode())) {
			ret.setRplCode(ReturnCodeEnum.VALIDATION.getCode())
			.setRplMsg("compile code is null");
			return ;
		}
		if(StringUtils.isBlank(req.getFunction())) {
			Object[] args = null;
			if(!req.getParamsList().isEmpty()) {
				args = parseParam(req.getParamsList());
			}
			CodeBuild.Result retB = null;
			try {
				retB = buildContract(req.getContractName(),req.getCode(),args);
			} catch (IOException e) {
				log.error("compile error ==>",e);
				ret.setRplCode(ReturnCodeEnum.EXCEPTION.getCode())
				.setRplMsg("compile exception ");
				return ;
			}
			ret.setAbi(retB.abi+"")
			.setBin(retB.data+"")
			.setCode(retB.exdata+"");
			ret.setRplCode(ReturnCodeEnum.DONE.getCode())
			.setRplMsg(retB.error+"");
		}else {
			CodeBuild.Result retB;
			try {
				retB = buildContract(req.getContractName(),req.getCode());
			} catch (IOException e) {
				log.error("compile error ==>",e);
				ret.setRplCode(ReturnCodeEnum.EXCEPTION.getCode())
				.setRplMsg("compile exception ");
				return ;
			}
			Object[] args = new Object[]{};
			if(!req.getParamsList().isEmpty()) {
				args = parseParam(req.getParamsList());
			}
			if(StringUtils.isNotBlank(retB.error)){
				ret.setRplCode(ReturnCodeEnum.ERROR.getCode());
				ret.setRplMsg(retB.error);
				return;
			}
			CallTransaction.Contract contract = new CallTransaction.Contract(retB.abi);
	        CallTransaction.Function inc = contract.getByName(req.getFunction());
	        byte[] functionCallBytes = inc.encode(args);

	        ret.setFunctionBin(encApi.hexEnc(functionCallBytes));
		}
		ret.setRplCode(ReturnCodeEnum.DONE.getCode());

	}

	private Object[] parseParam(List<Param> list) {
		Object[] obs = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			obs[i] = paramParse(list.get(i));
		}
		return obs;
	}

	private Object paramParse(Param param) {
		if(StringUtils.isNotEmpty(param.getValue())) {
			switch (param.getType()) {
			case "string":
				return param.getValue();
			case "double":
				return Double.parseDouble(param.getValue());
			case "float":
				return Float.parseFloat(param.getValue());
			case "int":
				return Integer.parseInt(param.getValue());
			case "long":
				return Long.parseLong(param.getValue());
			default:
				return param.getValue().toString();
			}

		}else if(param.getValuesCount()>0) {
			Object[] os = new Object[param.getValuesCount()];

				switch (param.getType()) {
				case "string":
					return param.getValuesList();
				case "double":
					for(int i =0; i< param.getValuesCount() ; i++) {
						os[i] = Double.parseDouble(param.getValues(i));
					}

					return os;
				case "float":
					for(int i =0; i< param.getValuesCount() ; i++) {
						os[i] = Float.parseFloat(param.getValues(i));
					}
					return os;
				case "int":
					for(int i =0; i< param.getValuesCount() ; i++) {
						os[i] = Integer.parseInt(param.getValues(i));
					}
					return os;
				case "long":
					for(int i =0; i< param.getValuesCount() ; i++) {
						os[i] = Long.parseLong(param.getValues(i));
					}
					return os;
				default:
					return param.getValuesList();
				}


		}
		return null;
	}

	public CodeBuild.Result buildContract(String contractName,String code, Object... args) throws IOException {
		return CodeBuild.newBuild(CodeBuild.Type.SOLIDITY).build(contractName,code, args);
	}
}
