package org.csc.wlt.utils;


public class ProcessException extends RuntimeException{

	private static final long serialVersionUID = 6322408625739005285L;
	String code;
	String msg;

	public ProcessException(){

	}
	public ProcessException(String code){
		super();
		this.code = code;
	}
	public ProcessException(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	public ProcessException(int code, String msg){
		this.code = String.valueOf(code);
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public int getCodeInt() {
		return Integer.parseInt(code);
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
