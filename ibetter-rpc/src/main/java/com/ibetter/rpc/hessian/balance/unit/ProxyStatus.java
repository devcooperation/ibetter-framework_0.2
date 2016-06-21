/**
 * Project Name:ibetter-spring
 * File Name:HessianProxyStatus.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.unit;

import java.util.Map;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月18日下午2:08:14</p>
 * <p>Description:TODO</p>
 */
public class ProxyStatus {
	
	public final static int PROXY_UP=1;
	public final static int PROXY_DOWN=0;
	
	private ProxyUnit proxyUnit;
	private int status;
	private Map<String,Object>    data;
	
 
	public ProxyStatus() {
		super();
	}

 

	public ProxyStatus(ProxyUnit proxyUnit) {
		super();
		this.proxyUnit = proxyUnit;
	}


	public ProxyStatus(ProxyUnit proxyUnit, int status) {
		super();
		this.proxyUnit = proxyUnit;
		this.status = status;
	}

	public ProxyStatus(ProxyUnit proxyUnit, Map<String,Object>  data) {
		super();
		this.proxyUnit = proxyUnit;
		this.data = data;
	}

	public ProxyUnit getProxyUnit() {
		return proxyUnit;
	}

	public void setProxyUnit(ProxyUnit proxyUnit) {
		this.proxyUnit = proxyUnit;
	}

	public Map<String,Object>  getData() {
		return data;
	}

	public void setData(Map<String,Object>  data) {
		this.data = data;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
}
