package org.csc.wlt.config;

import lombok.Builder;

import java.util.concurrent.TimeUnit;
@Builder
public class LuaShellUserConfig {
    /**
     * 用户userId缓存的前缀
     */
    private String userPrx;
    /**
     * 用户的id
     */
    private String userId;
    /**
     * 用户token的缓存前缀
     */
    private String tokenPrx;
    /**
     * token值
     */
    private String tokenValue;

    public String getLuaShell() {
        String template = "local oldTokenVal = redis.call(\"get\",'%s');" +
                "redis.call(\"del\",'%s' .. tostring(oldTokenVal));" +
                "redis.call(\"setex\",'%s','%s','%s');" +
                "redis.call(\"setex\",'%s','%s','%s');return true;";
        String timeLive = String.valueOf(TimeUnit.DAYS.toMillis(30));
        return String.format(template,userPrx+userId,
                tokenPrx, tokenPrx+tokenValue,timeLive ,userId,
                userPrx+userId,timeLive,tokenValue);
    }
}
