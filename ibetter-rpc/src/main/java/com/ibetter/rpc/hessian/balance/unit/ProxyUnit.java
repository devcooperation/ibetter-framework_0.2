/**
 * Project Name:ibetter-spring
 * File Name:ProxyService.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.unit;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月18日下午2:04:16</p>
 * <p>Description:TODO</p>
 */
public class ProxyUnit {
	
	private String accessPath;
	private Object proxyObject;
	private int weight;
	private volatile long timestamp;
	private volatile int status;//0已下线 1已上线 
	
	public Object getProxyObject() {
		return proxyObject;
	}
	public int getWeight() {
		return weight;
	}
	public void setProxyObject(Object proxyObject) {
		this.proxyObject = proxyObject;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getAccessPath() {
		return accessPath;
	}
	public void setAccessPath(String accessPath) {
		this.accessPath = accessPath;
	}
	@Override
	public String toString() {
		StringBuilder sBuilder=new StringBuilder();
		sBuilder.append( "ProxyUnit [accessPath=")
				.append(accessPath)
				.append(", timestamp=")
				.append(timestamp)
				.append(", weight=" )
				.append(weight)
				.append(", status=")
				.append(status)
				.append("]");
		
		return sBuilder.toString();
	}
	 
	
}
