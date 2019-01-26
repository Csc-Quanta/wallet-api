package org.csc.wlt.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.csc.wlt.filter.AbBusinessFilter;

import java.util.HashMap;
import java.util.Map;

@NActorProvider
@Slf4j
@Data
@Instantiate(name = "filterUtil")
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
public class FilterUtil implements ActorService{

    @ActorRequire(name = "userLoginFilter")
    private AbBusinessFilter userLoginFilter;

    Map<String, AbBusinessFilter> filterMap = new HashMap<>();
    @Validate
    public void startUp(){
        new Thread(()->{
            while (userLoginFilter==null){
                try {
                    log.info("userLoginFilter not ready userLoginFilter={}",userLoginFilter);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            filterMap.put("ULPWLT",userLoginFilter);
            filterMap.put("QUWWLT",userLoginFilter);
            filterMap.put("UWDWLT",userLoginFilter);
            filterMap.put("CNAWLT",userLoginFilter);
            filterMap.put("ADQWLT",userLoginFilter);
            filterMap.put("TXCWLT",userLoginFilter);
            filterMap.put("QTSWLT",userLoginFilter);
            filterMap.put("LBQWLT",userLoginFilter);
            filterMap.put("QMKWLT",userLoginFilter);
            filterMap.put("CCSWLT",userLoginFilter);
            filterMap.put("QTBWLT",userLoginFilter);
            filterMap.put("TBCWLT",userLoginFilter);
            filterMap.put("TCCWLT",userLoginFilter);
            filterMap.put("TBTWLT",userLoginFilter);
            filterMap.put("TTTWLT",userLoginFilter);
            filterMap.put("TBRWLT",userLoginFilter);
            filterMap.put("TRTWLT",userLoginFilter);
            log.info("fileMap ==>:{}", filterMap);
        }).start();
    }
}
