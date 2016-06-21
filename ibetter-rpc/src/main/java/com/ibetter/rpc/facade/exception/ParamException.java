package com.ibetter.rpc.facade.exception;
/**
 * 接口参数异常
 * @author zhaojun
 * 2016年2月18日 上午10:18:37
 */
public class ParamException extends RuntimeException {

	public ParamException() {
		super();
	}

	public ParamException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParamException(String message) {
		super(message);
	}

	public ParamException(Throwable cause) {
		super(cause);
	}

}
