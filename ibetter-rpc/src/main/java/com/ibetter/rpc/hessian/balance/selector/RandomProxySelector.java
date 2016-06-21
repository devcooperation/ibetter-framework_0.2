/**
 * Project Name:ibetter-spring
 * File Name:RandomProxySelector.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ibetter.rpc.hessian.balance.exception.NoAvailableProxyException;
import com.ibetter.rpc.hessian.balance.unit.ProxyStatus;
import com.ibetter.rpc.hessian.balance.unit.ProxyUnit;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月19日下午4:23:22</p>
 * <p>Description:TODO</p>
 */
public class RandomProxySelector extends ProxyBaseSelector implements ProxySelector {

	protected List<ProxyUnit> proxyUnits;
	protected int total;
	protected Random random=new Random();
	
	@Override
	public void initProxys(List<ProxyUnit> proxyUnits) {
		this.proxyUnits=proxyUnits;
		this.total=proxyUnits.size(); 
	}

	
	@Override
	public ProxyUnit getProxyUnit() throws NoAvailableProxyException {
		List<ProxyUnit> _proxyUnits=new ArrayList<ProxyUnit>();
		_proxyUnits.addAll(this.proxyUnits);
		ProxyUnit proxyUnit = getProxyUnit(_proxyUnits);
		proxyUnit.setTimestamp(System.currentTimeMillis());
		return proxyUnit;
	}
	public ProxyUnit getProxyUnit(List<ProxyUnit> _proxyUnits) throws NoAvailableProxyException{
		int _total=_proxyUnits.size();
		if (_total<=0) {
			throw new NoAvailableProxyException("All Proxies are down!");
		}
		int _c = this.random.nextInt(_total);
		ProxyUnit proxyUnit= _proxyUnits.get(_c);
		int status = proxyUnit.getStatus();
		if (status<=0) {
			_proxyUnits.remove(_c);
			return getProxyUnit(_proxyUnits);
		 
		}
		return proxyUnit;
	}


 
	

 
}
