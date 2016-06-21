package com.ibetter.http.request.result;



/**
 * 接口返回数据
 * @author zhaojun
 * 2015年10月26日 下午6:07:57
 */
public class JResult<T> {
	
	/**
	 * 状态码
	 */
	public int status;
	/**
	 * 返回数据
	 */
	public T  data;
	
	/**
	 * 返回时间戳
	 */
	public long responseTime=System.currentTimeMillis();
	
	/**
	 * 提示消息
	 */
	public String message="";
	
	/**
	 * 错误消息
	 */
	public String error="";
	
	
	public JResult() {
		super();
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public long getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
