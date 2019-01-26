package org.csc.wlt.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class AppReqHeader {
    //app唯一id
    private String appId;
    // 业务id
    private String busi;
    //操作系统，安卓，ios，windows，mac
    private String osType;
    //当前app的版本，windows 和mac一直传1.0就行
    private String appVersion;
    //当前操作系统的版本
    private String osVersion;
    //登录以后返回的token
    private String grantToken;
    public boolean checkParam(){
        return StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(busi);
    }
}
