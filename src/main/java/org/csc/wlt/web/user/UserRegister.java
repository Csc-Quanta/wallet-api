package org.csc.wlt.web.user;

import com.googlecode.protobuf.format.JsonFormat;
import com.zcs.user.api.result.ResponseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.async.CompleteHandler;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.PacketHelper;
import onight.tfw.otransio.api.beans.FramePacket;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.common.Constants;
import org.csc.wlt.common.ErrorCodeConstants;
import org.csc.wlt.common.UrlConstants;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.model.AppReqHeader;
import org.csc.wlt.service.impl.AbTemplateService;
import org.csc.wlt.utils.ResultUtil;
import org.csc.wlt.utils.SendMsgUtils;

import java.util.HashMap;
import java.util.Map;

@NActorProvider
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class UserRegister extends AbTemplateService<Wallet.BaseData,Void, User.UserRegisterReq.Builder> {

    @ActorRequire(name = "sendMsgUtils", scope = "global")
    SendMsgUtils sendMsgUtils;
    @ActorRequire(name = "resultUtil", scope = "global")
    ResultUtil resultUtil;


    @ActorRequire(name = "userExistService", scope = "global")
    AbTemplateService<Wallet.BaseData,String,User.UserExistReq.Builder> userExistService;
    @Override
    public String[] getCmds() {
        return new String[]{User.PUSERCommand.URT.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }

    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        ResultOuterClass.Result.Builder ret = ResultOuterClass.Result.newBuilder();
        User.UserExistRes.Builder builder = User.UserExistRes.newBuilder();
        try {
            String flag;
            if((flag = userExistService.execute(pb,pack,ret)) == null || flag.equals(Constants.USER_EXIST_FLAG)){
                builder.setRplCode(ret.getRplCode());
                builder.setRplMsg(ret.getRplMsg());
                builder.setFlag(flag);
                log.info("{} builder.toString:{}",this.getClass().getSimpleName(),builder.build().toString());
                handler.onFinished(PacketHelper.toPBReturn(pack, builder.build()));
                return;
            }
            execute(pb,pack,ret);
        } catch (JsonFormat.ParseException e) {
            log.error("user reg fail param :{} :{}",pb.toString(),e);
            ret.setRplCode(ReturnCodeEnum.ERROR.getCode());
        }
        handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
    }

    /**
     * 获得build
     * @return
     */
    protected User.UserRegisterReq.Builder getBuild(){
        return User.UserRegisterReq.newBuilder();
    }

    /**
     * 组装参数
     * @param builder
     * @return
     */
    protected Map<String,Object> param(User.UserRegisterReq.Builder builder){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userName",builder.getUserName());
        paramMap.put("moblie",builder.getMobile());
        paramMap.put("pwd",builder.getPwd());
        paramMap.put("nick",builder.getNick());
        paramMap.put("birthday",builder.getBirthday());
        paramMap.put("gender",builder.getGender());
        paramMap.put("email",builder.getEmail());
        paramMap.put("name",builder.getName());
        paramMap.put("idCard",builder.getIdCard());
        return paramMap;
    }

    /**
     * 获得请求的url
     * @return
     */
    protected String getReqUrl(){
        return UrlConstants.USER_REG_URL;
    }

    @Override
    protected boolean definedCheck(User.UserRegisterReq.Builder builder, AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret) {

        boolean checkResult = sendMsgUtils.checkSmsPassStatus(builder.getSmsPassToken(),builder.getUserName()+appReqHeader.getAppId());
        if(!checkResult){
            ret.mergeFrom(resultUtil.returnResult(ErrorCodeConstants.OPERATE_TIME_OUT));
        }
        return checkResult;
    }

    /**
     * 对结果进行处理
     * @param responseInfo
     * @return
     */
    protected Void dealResult(ResponseInfo responseInfo){
       return null;
    }
}
