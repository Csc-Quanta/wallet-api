package org.csc.wlt.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.ojpa.ordb.ORDBDataService;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wallet.service.User;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscWltData;
import org.csc.wlt.entity.CscWltDataModel;
import org.csc.wlt.entity.CscWltUser;
import org.csc.wlt.entity.CscWltUserModel;
import org.csc.wlt.enums.ReturnCodeEnum;
import org.csc.wlt.service.WalletDataService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NActorProvider
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "walletDataService")
@Slf4j
@Data
public class WalletDataServiceImpl implements WalletDataService, ActorService {
    @ActorRequire(name = "daos", scope = "global")
    Daos daos;

    /**
     * 查询用户钱包数据
     * @param req
     * @param appId
     * @param ret
     */
    @Override
    public void queryUserWltData(User.QueryUserWltDataReq.Builder req, String appId, User.QueryUserWltDataRes.Builder ret) {
        String userId = req.getUserId();
        //查询
        CscWltUserModel cscWltUserModel = new CscWltUserModel();
        cscWltUserModel.setCUserId(userId);
        cscWltUserModel.setStatus("1");
        List<Object> objects = daos.getWltUserDao().selectByExample(cscWltUserModel);
        if(objects== null || objects.isEmpty()){
            log.info("current user:{} not have wlt",userId);
            ret.setRplCode(ReturnCodeEnum.DONE.getCode());
            return;
        }
        List<User.WalletDate> resultList = new ArrayList<>();
        List<CscWltUser> cscWltUsers = JSON.parseArray(JSON.toJSONString(objects),CscWltUser.class);
        for(CscWltUser cscWltUser : cscWltUsers){
            User.WalletDate.Builder builder = User.WalletDate.newBuilder();
            builder.setWalletName(cscWltUser.getWltName());
            builder.setWalletType(cscWltUser.getWltType());
            builder.setCreateType(cscWltUser.getCreateType());
            builder.setWalletProtocol(cscWltUser.getWltProtocol());
            CscWltDataModel model = new CscWltDataModel();
            model.setStatus("1");
            model.setWltUserId(cscWltUser.getAutoId());
            List<Object> wltDataObjList = daos.wltDateDao.selectByExample(model);
            if(wltDataObjList == null || wltDataObjList.isEmpty()){
                log.info("current autoId :{} not have wltData",model.getWltUserId());
                continue;
            }
            List<CscWltData> cscWltDataList = JSON.parseArray(JSON.toJSONString(wltDataObjList),CscWltData.class);
            builder.addAllData(cscWltDataList.stream().map(CscWltData::getData).collect(Collectors.toList()));
            resultList.add(builder.build());
        }
        ret.addAllWalletData(resultList);
        ret.setRplCode(ReturnCodeEnum.DONE.getCode());
    }
    /**
     * 上传钱包数据
     * @param req
     * @param appId
     */
    @Override
    public boolean doUploadUserWltData(User.UploadUserWltDataReq.Builder req, String appId,ResultOuterClass.Result.Builder ret){
        String userId = req.getUserId();
        List<User.WalletDate.Builder> builders = req.getWalletDataBuilderList();
        List<Object> cscWltUserModels = new ArrayList<>();
        for(User.WalletDate.Builder builder : builders){
            CscWltUserModel model = new CscWltUserModel();
            model.setWltType(builder.getWalletType());
            model.setStatus("1");
            model.setCUserId(userId);
            model.setWltName(builder.getWalletName().trim());
            if(daos.getWltUserDao().selectOneByExample(model)!=null){
                log.info("wltName:{},userId:{} wltType:{} already exist",
                        builder.getWalletName().trim(),userId,builder.getWalletType());
                continue;
            }
            CscWltUserModel cscWltUserModel = new CscWltUserModel();
            cscWltUserModel.setAppId(appId);
            cscWltUserModel.setCUserId(userId);
            cscWltUserModel.setWltName(builder.getWalletName());
            cscWltUserModel.setWltType(builder.getWalletType());
            cscWltUserModel.setCreateType(builder.getCreateType());
            cscWltUserModel.setWltProtocol(builder.getWalletProtocol());
            cscWltUserModel.setStatus("1");
            cscWltUserModel.setData(builder.getDataList());
            cscWltUserModel.setCreateTime(new Date());
            cscWltUserModel.setUpdateTime(new Date());
            cscWltUserModels.add(cscWltUserModel);
        }
        if(cscWltUserModels.isEmpty()){
            ret.setRplCode(ReturnCodeEnum.DONE.getCode());
            return false;
        }
        log.info("cscWltUserModel json.toJsonString:{}", JSON.toJSONString(cscWltUserModels));
        //增加到wltUser
        return daos.getWltUserDao().doInTransaction(() -> {
             ORDBDataService service = (ORDBDataService) daos.getWltUserDao().getDaosupport();
             try {
                 service.getDao().batchInsert(cscWltUserModels);
             } catch (Exception e) {
                 ret.setRplCode(ReturnCodeEnum.EXCEPTION.getCode());
                 log.error("service.getDao() error:{}",e);
                 return null;
             }
             List<Object> cscWltDatas = new ArrayList<>();
             cscWltUserModels.forEach(obj->{
                 CscWltUserModel cscWltUserModel = (CscWltUserModel) obj;
                 cscWltUserModel.getData().forEach(data->{
                     CscWltData cscWltData = new CscWltData();
                     cscWltData.setWltUserId(cscWltUserModel.getAutoId());
                     cscWltData.setData(data);
                     cscWltData.setStatus("1");
                     cscWltData.setCreateTime(new Date());
                     cscWltData.setUpdateTime(new Date());
                     cscWltDatas.add(cscWltData);
                 });
             });
             ret.setRplCode(ReturnCodeEnum.DONE.getCode());
            return daos.getWltDateDao().batchInsert(cscWltDatas);
         })!=null;
    }

    @Override
    public boolean checkUserId(String cUserId, String userId, ResultOuterClass.Result.Builder ret) {
        if(!cUserId.equals(userId)){
            ret.setRplCode(ReturnCodeEnum.ERROR.getCode());
            ret.setRplMsg("userId is not same");
            return false;
        }
        return true;
    }
}
