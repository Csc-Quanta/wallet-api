package org.csc.wlt.model;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class UserCenterModel {
    /**
     * 应用id
     */
    private String appId;
    /**
     * 服务id
     */
    private String serviceId;
    /**
     * 授权码
     */
    private String accessToken;
}
