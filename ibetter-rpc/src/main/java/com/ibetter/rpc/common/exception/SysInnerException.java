package com.ibetter.rpc.common.exception;
/**
 * @author zhaojun
 * 2016年2月18日 上午10:19:00
 * 系统内部异常
 */
public class SysInnerException extends RuntimeException {
	
	public SysInnerException() {
		super();
	}

	public SysInnerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SysInnerException(String message) {
		super(message);
	}

	public SysInnerException(Throwable cause) {
		super(cause);
	}
}
