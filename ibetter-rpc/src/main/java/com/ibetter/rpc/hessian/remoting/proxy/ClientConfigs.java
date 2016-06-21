/**
 * Project Name:ibetter-rpc
 * File Name:ClientConfigs.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.remoting.proxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ibetter.rpc.hessian.balance.detector.DefaultProxyUnitDetector;
import com.ibetter.rpc.hessian.balance.detector.ProxyUnitDetector;
import com.ibetter.rpc.hessian.balance.selector.ProxySelector;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月20日下午6:21:39</p>
 * <p>Description:TODO</p>
 */
public class ClientConfigs implements Serializable {
	
	protected String balance=ProxySelector._time_cost;//请求最短时间
	protected int proxySelectRetry=10;
	protected List<ClientConfig> configs=new ArrayList<ClientConfig>();
	protected ProxyUnitDetector proxyDetector=new DefaultProxyUnitDetector();
	
	public List<ClientConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<ClientConfig> configs) {
		this.configs = configs;
	}

	public ProxyUnitDetector getProxyDetector() {
		return proxyDetector;
	}

	public void setProxyDetector(ProxyUnitDetector proxyDetector) {
		this.proxyDetector = proxyDetector;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public int getProxySelectRetry() {
		return proxySelectRetry;
	}

	public void setProxySelectRetry(int proxySelectRetry) {
		this.proxySelectRetry = proxySelectRetry;
	}
	
}
