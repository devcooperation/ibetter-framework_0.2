/**
 * Project Name:ibetter-spring
 * File Name:ProxySelector.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.selector;

import java.util.List;

import com.ibetter.rpc.hessian.balance.exception.NoAvailableProxyException;
import com.ibetter.rpc.hessian.balance.unit.ProxyStatus;
import com.ibetter.rpc.hessian.balance.unit.ProxyUnit;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月19日上午10:19:30</p>
 * <p>Description:TODO</p>
 */
public interface ProxySelector {
	
	public String _round="round";//轮询
	public String _random="random";//随机
	public String _weight="roundWeight";//轮询权重
	public String _random_weight="randomWeight";//随机权重
	public String _time_cost = "timeCost";//请求消耗时间  最短消耗时间
	
	public void initProxys(List<ProxyUnit> proxyUnits);
	public ProxyUnit getProxyUnit() throws NoAvailableProxyException;
	public void changeStatus(ProxyStatus proxyStatus);
}
