package org.csc.wlt.common;

public interface UrlConstants {
    /**
     * 用户注册
     */
    String USER_REG_URL = "/ucenter/api/v2/register";
    /**
     * 用户是否已经存在
     */
    String USER_EXIST_URL = "/ucenter/api/v2/userIsExist";
    /**
     * 用户重新设置登录密码
     */
    String RESET_LOGIN_PASSWORD = "/ucenter/api/v2/resetLoginPwd";
    /**
     * 修改登录密码
     */
    String UPDATE_LOGIN_PASSWORD = "/ucenter/api/v2/user/updateLoginPwd";
    /**
     * 用户登录
     */
    String USER_LOGIN = "/ucenter/api/v2/login";
    /**
     * 用户退出登录
     */
    String USER_OUT_LOGIN = "/ucenter/api/v2/user/logout";
    /**
     * 发送短信
     */
    String SEND_MSG = "/service/sendMsg.do";

    /**校验验证码*/
    String VALID_CODE = "/service/checkValidCode.do";
    /**
     * 到用户中心根据grantToken查询用户信息
     */
    String QUERY_USER_INFO="/ucenter/api/v2/user/query/info";
}
