/**
 * Project Name:ibetter-rpc
 * File Name:ProxyTimeCostStatus.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.unit;

import java.util.Map;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月24日下午5:11:58</p>
 * <p>Description:TODO</p>
 */
public class ProxyTimeCostStatus extends ProxyStatus {
	private long timeCost;

 
	public ProxyTimeCostStatus() {
		super();
	}

 
 
	public ProxyTimeCostStatus(ProxyUnit proxyUnit,int status,long timeCost) {
		this(proxyUnit,status);
		this.timeCost = timeCost;
	}


	public ProxyTimeCostStatus(ProxyUnit proxyUnit, int status) {
		super(proxyUnit, status);
	}

 
	public ProxyTimeCostStatus(ProxyUnit proxyUnit, Map<String, Object> data) {
		super(proxyUnit, data);
	}

 
	public ProxyTimeCostStatus(ProxyUnit proxyUnit) {
		super(proxyUnit);
	}

	public long getTimeCost() {
		return timeCost;
	}

	public void setTimeCost(long timeCost) {
		this.timeCost = timeCost;
	}
	
}
