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
public class ResetLoginPassword extends AbTemplateService<Wallet.BaseData,Void, User.ResetLoginPasswordReq.Builder>{
    @ActorRequire(name = "sendMsgUtils", scope = "global")
    SendMsgUtils sendMsgUtils;
    @ActorRequire(name = "resultUtil", scope = "global")
    ResultUtil resultUtil;
    @Override
    public String[] getCmds() {
        return new String[]{User.PUSERCommand.RLP.name()};
    }

    @Override
    public String getModule() {
        return Wallet.PWLTModule.WLT.name();
    }

    @Override
    public void onPBPacket(final FramePacket pack, final Wallet.BaseData pb, final CompleteHandler handler) {
        ResultOuterClass.Result.Builder ret = ResultOuterClass.Result.newBuilder();
        try {
            execute(pb,pack,ret);
        } catch (JsonFormat.ParseException e) {
            log.error("user resetLoginPassword fail param :{} :{}",pb.toString(),e);
            ret.setRplCode(ReturnCodeEnum.ERROR.getCode());
        }
        log.info("{} ret.toString:{}",this.getClass().getSimpleName(),ret.build().toString());
        handler.onFinished(PacketHelper.toPBReturn(pack, ret.build()));
    }
    /**
     * 获得build
     * @return
     */
    protected User.ResetLoginPasswordReq.Builder getBuild(){
        return User.ResetLoginPasswordReq.newBuilder();
    }

    /**
     * 组装参数
     * @param builder
     * @return
     */
    protected Map<String,Object> param(User.ResetLoginPasswordReq.Builder builder){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userName",builder.getUserName());
        paramMap.put("newPwd",builder.getNewPwd());
        return paramMap;
    }

    /**
     * 获得请求的url
     * @return
     */
    protected String getReqUrl(){
        return UrlConstants.RESET_LOGIN_PASSWORD;
    }

    /**
     * 参数校验
     * @param builder 解密以后的参数
     * @param ret 返回结果
     * @return
     */
    @Override
    protected boolean definedCheck(User.ResetLoginPasswordReq.Builder builder,
                                   AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret) {
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
