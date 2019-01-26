package org.csc.wlt.common;

/**
 * 错误码常量
 */
public interface ErrorCodeConstants {
    int ERR = -1;//错误
    int SUC = 1;//成功
    int EXCEPTION = -2;//系统异常
    int VALIDATION = -3;//参数校验失败
    int LOGIN_PASSWORD_ERROR = 4;//用户名或密码错误
    int LOGIN_TIME_OUT = 5;//登录超时请重新登录
    int USER_NOT_REG=6;//用户未注册
    int USER_NAME_ALREADY_REG=7;//该手机号码已经被注册
    int OLD_PWD_ERROR = 8;//原密码不正确
    int USER_ALREADY_EXIST = 9;//用户已存在
    int VALID_CODE_ERROR = 10;//验证码错误
    int SEND_AFTER_60_SEC = 11;//请在60s后再次发送
    int VALID_CODE_NOT_NULL  = 12;//验证码不能为空
    int PHONE_FORMAT_ERROR=13;//电话号码格式错误
    int SEND_CODE_TIMEOUT = 14;//验证码已过期
    int OPERATE_TIME_OUT = 15;//操作超时，请刷新重试
}
