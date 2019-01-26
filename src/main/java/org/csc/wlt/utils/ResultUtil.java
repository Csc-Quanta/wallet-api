package org.csc.wlt.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.csc.wallet.service.ResultOuterClass;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.entity.CscCodeMap;
import org.csc.wlt.entity.CscCodeMapModel;
import org.csc.wlt.entity.CscSwitchCode;
import org.csc.wlt.entity.CscSwitchCodeModel;
import org.csc.wlt.enums.ReturnCodeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对错误码进行处理
 */
@NActorProvider
@Slf4j
@Data
@Instantiate(name = "resultUtil")
@Provides(specifications = {ActorService.class}, strategy = "SINGLETON")
public class ResultUtil implements ActorService {
    @ActorRequire(name = "daos", scope = "global")
    Daos daos;
    private volatile List<Map<Integer, String>> resultCodeList = new ArrayList<>(2);
    private volatile int curIndex = 0;
    private volatile List<Map<String, Integer>> otherCodeList = new ArrayList<>(2);

    /**
     * 返回本系统错误码 如果不存在 返回系统异常错误码
     *
     * @param retCode
     * @return
     */
    public ResultOuterClass.Result returnResult(int retCode) {
        ResultOuterClass.Result.Builder builder = ResultOuterClass.Result.newBuilder();
        String message = resultCodeList.get(curIndex).get(retCode);
        builder.setRplMsg(message == null ? ReturnCodeEnum.ERROR.getMsg() : message);
        builder.setRplCode(message == null ? ReturnCodeEnum.ERROR.getCode() : retCode);
        return builder.build();
    }

    /**
     * 根据其他系统错误码 返回本系统错误码
     *
     * @param chanId
     * @param errCode
     * @return
     */
    public ResultOuterClass.Result returnByOthCode(String chanId, String errCode) {
        return returnResult(convertCode(chanId, errCode));
    }

    /**
     * 清除缓存然后重新放入,不影响其他业务
     */
    public void cleanAndPut() {
        insertToMap();
    }

    /**
     * 转换成本系统得错误码 如果不存在转换成系统异常错误码
     *
     * @param chanId
     * @param errCode
     * @return
     */
    public int convertCode(String chanId, String errCode) {
        Integer code = otherCodeList.get(curIndex).get(chanId + "-" + errCode);
        return code == null ? ReturnCodeEnum.ERROR.getCode() : code;
    }

    /**
     * 将错误码插入到缓存中
     */
    private void insertToMap() {
        CscCodeMapModel cscCodeMapModel = CscCodeMapModel.getInstance();
        cscCodeMapModel.setStatus("1");
        List<Object> objs = daos.codeMapDao.selectByExample(cscCodeMapModel);
        if (objs == null || objs.isEmpty()) {
            log.warn("csc_code_map not having err_code");
            return;
        }
        CscSwitchCodeModel cscSwitchCodeModel = CscSwitchCodeModel.getInstance();
        cscSwitchCodeModel.setStatus("1");
        List<Object> switchCodeObjs = daos.switchCodeDao.selectByExample(cscSwitchCodeModel);
        if (switchCodeObjs == null || switchCodeObjs.isEmpty()) {
            log.warn("csc_switch_code not having switch_code");
            return;
        }
        int s = 0;
        for (int i = 0; i < resultCodeList.size(); i++) {
            if (i != curIndex) {
                s = i;
                break;
            }
        }
        resultCodeList.get(s).clear();
        List<CscCodeMap> CscCodeMaps = JSON.parseArray(JSON.toJSONString(objs), CscCodeMap.class);
        for (CscCodeMap cscCodeMap : CscCodeMaps) {
            resultCodeList.get(s).put(Integer.parseInt(cscCodeMap.getErrCode()), cscCodeMap.getErrMessage());
        }
        otherCodeList.get(s).clear();
        List<CscSwitchCode> cscSwitchCodes = JSON.parseArray(JSON.toJSONString(switchCodeObjs), CscSwitchCode.class);
        for (CscSwitchCode cscSwitchCode : cscSwitchCodes) {
            otherCodeList.get(s).put(cscSwitchCode.getChanId() + "-" + cscSwitchCode.getOthCode(),
                    Integer.parseInt(cscSwitchCode.getErrCode()));
        }

        int tempCurIndex = curIndex;
        curIndex = s;
        resultCodeList.get(tempCurIndex).clear();
        resultCodeList.get(tempCurIndex).putAll(resultCodeList.get(s));
        otherCodeList.get(tempCurIndex).clear();
        otherCodeList.get(tempCurIndex).putAll(otherCodeList.get(s));
    }

    @Validate
    public void startUp() {
        new Thread(() -> {
            while (daos == null || daos.ready == false) {
                log.info("ResultUtil daos is not ready daos={}", daos);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 2; i++) {
                resultCodeList.add(new HashMap<>());
                otherCodeList.add(new HashMap<>());
            }
            insertToMap();
            log.info("init errCode end...");
        }).start();
    }
}
