package org.csc.wlt.enums;

public enum ReturnCodeEnum {
	DONE(1, "DONE"), ERROR(-1, "ERROR"), EXCEPTION(-2, "exception"), VALIDATION(-3, "validation"),
	LOGIN_PASSWORD_ERROR(4,"用户名或密码错误"),
	LOGIN_TIME_OUT(5,"登录超时请重新登录");

	private int code;
	private String msg;

	private ReturnCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
