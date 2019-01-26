package org.csc.wlt.service.impl;

import com.google.protobuf.GeneratedMessageV3.Builder;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.oapi.scala.commons.SessionModules;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.beans.FramePacket;
import org.csc.backend.redis.RedisTools;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.Wallet;
import org.csc.wlt.common.CaCheConstants;
import org.csc.wlt.common.Constants;
import org.csc.wlt.helper.CommonHelper;
import org.csc.wlt.model.AppReqHeader;
import org.csc.wlt.utils.JsonUtil;
import org.csc.wlt.utils.UserCacheUtil;

@Slf4j
@Data
public abstract class CommonCheckService<A extends Message,D extends Builder> extends SessionModules<A> {
    @ActorRequire(name = "commHelper", scope = "global")
    protected CommonHelper commonHelper;
    @ActorRequire(name = "redisTools", scope = "global")
    RedisTools redisTools;
    @ActorRequire(name = "userCacheUtil", scope = "global")
    protected UserCacheUtil userCacheUtil;

    /**
     * 对参数进行校验
     * @param packet
     * @param data
     * @param ret
     * @return
     */
    protected boolean check(FramePacket packet, Wallet.BaseData data, ResultOuterClass.Result.Builder ret, D builder) throws JsonFormat.ParseException {
        //校验参数
        String[] strings = commonHelper.checkParam(packet,data,ret);
        if(strings[0]!=null){
            return false;
        }
        //得到解密以后的数据
        JsonUtil.jsonToObject(strings[1], builder);
        //对解密以后的参数进行校验
        return definedCheck(builder,commonHelper.getHeader(packet),ret);
    }

    /**
     * 通过packet 获得cUserId
     * @param packet
     * @return
     */
    protected String getUserId(FramePacket packet){
        return userCacheUtil.get(CaCheConstants.USER_TOKEN_PRX,packet.getHttpServerletRequest().getHeader(Constants.GRANT_TOKEN));
    }

    /**
     * 自定义参数校验
     * @param builder 解密以后的参数
     * @param ret 返回结果
     * @return boolean true 校验通过，false校验失败
     */
    protected abstract boolean definedCheck(D builder, AppReqHeader appReqHeader, ResultOuterClass.Result.Builder ret);

    /**
     * 获得build
     * @return
     */
    protected abstract D getBuild();
}
