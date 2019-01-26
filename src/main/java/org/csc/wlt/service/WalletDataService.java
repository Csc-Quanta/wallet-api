package org.csc.wlt.service;

import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;

public interface WalletDataService {
    /**
     * 查询用户钱包数据
     * @param req
     * @param appId
     * @param ret
     */
    void queryUserWltData(User.QueryUserWltDataReq.Builder req,String appId, User.QueryUserWltDataRes.Builder ret);

    /**
     * 上传钱包数据
     * @param req
     * @param appId
     * @return boolean 上传是否成功
     */
    boolean doUploadUserWltData(User.UploadUserWltDataReq.Builder req, String appId,ResultOuterClass.Result.Builder ret) throws Exception;

    /**
     * 检查userid
     * @param cUserId
     * @param userId
     * @return
     */
    boolean checkUserId(String cUserId, String userId, ResultOuterClass.Result.Builder ret);
}
