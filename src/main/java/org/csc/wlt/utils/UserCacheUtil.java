package org.csc.wlt.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.backend.redis.RedisTools;
import org.csc.wlt.common.CaCheConstants;
import org.csc.wlt.config.LuaShellUserConfig;

import java.util.concurrent.TimeUnit;

@NActorProvider
@Slf4j
@Data
@Instantiate(name = "userCacheUtil")
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
public class UserCacheUtil implements ActorService{

    @ActorRequire(name = "redisTools", scope = "global")
    RedisTools redisTools;

    /**
     * 自己定义前缀放入20天
     * @param prx
     * @param key
     * @param value
     */
    public void put(String prx,String key,String value){
        redisTools.set(prx+key,value,30, TimeUnit.DAYS);
    }

    /**
     * 自定义前缀
     * @param prx
     * @param key
     * @return
     */
    public boolean remove(String prx,String key){
        redisTools.delete(prx+key);
        return true;
    }
    /**
     * 通过key到缓存中取value
     * @return
     */
    public String get(String prx,String key){
        return redisTools.get(prx+key);
    }

    /**
     * 执行用户登录以后的脚本
     * @param userId
     * @param token
     */
    public void eval(String userId,String token){
        redisTools.eval(LuaShellUserConfig.builder().tokenPrx(CaCheConstants.USER_TOKEN_PRX)
                .tokenValue(token).userId(userId).userPrx(CaCheConstants.USER_ID_PRX).build().getLuaShell());
    }
    /**
     * 检验通过则重新放入
     */
    public boolean checkAndPut(String prx,String key){
        String e;
        if((e=get(prx,key))==null){
            return false;
        }
        put(prx,key,e);
        return true;
    }

}
