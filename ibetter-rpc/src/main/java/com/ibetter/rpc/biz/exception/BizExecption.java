package com.ibetter.rpc.biz.exception;
/**
 * 远程调用业务异常
 * @author zhaojun
 * 2016年2月18日 上午10:19:41
 */
public class BizExecption extends RuntimeException {

	public BizExecption() {
		super();
	}

	public BizExecption(String message, Throwable cause) {
		super(message, cause);
	}

	public BizExecption(String message) {
		super(message);
	}

	public BizExecption(Throwable cause) {
		super(cause);
	}

}
