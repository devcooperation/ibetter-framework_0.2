/**
 * Project Name:ibetter-spring
 * File Name:RoundProxySelector.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.ibetter.rpc.hessian.balance.exception.NoAvailableProxyException;
import com.ibetter.rpc.hessian.balance.unit.ProxyStatus;
import com.ibetter.rpc.hessian.balance.unit.ProxyUnit;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月19日下午4:21:32</p>
 * <p>Description:</p>
 */
public class RoundProxySelector extends ProxyBaseSelector  implements ProxySelector {
	
	
	protected List<ProxyUnit> proxyUnits;
	protected AtomicLong proxyRound=new AtomicLong(-1);
	protected int total;
	
	@Override
	public void initProxys(List<ProxyUnit> _proxyUnits) {
		this.proxyUnits=_proxyUnits;
		this.total=this.proxyUnits.size();
	}
	 
	@Override
	public ProxyUnit getProxyUnit() throws NoAvailableProxyException {
		List<ProxyUnit>_HessianProxyUnits=new ArrayList<ProxyUnit>(this.proxyUnits);
		ProxyUnit proxyUnit = this.getProxyUnits(_HessianProxyUnits);
		proxyUnit.setTimestamp(System.currentTimeMillis());
		return proxyUnit;
	}
	
	public ProxyUnit getProxyUnits(List<ProxyUnit>_HessianProxyUnits) throws NoAvailableProxyException{
		long _total=_HessianProxyUnits.size();
		
		if (_total<=0) {
			throw new NoAvailableProxyException("All Proxies are down!");
		}
		
		long _cRound = proxyRound.incrementAndGet();
		int x = (int) ((/*long溢出*/(_cRound<0)?_cRound*(-1):_cRound)%_total);
		ProxyUnit proxyUnit = _HessianProxyUnits.get(x);
		int status = proxyUnit.getStatus();
		if (status<=0) {
			this.removeProxyUnit(proxyUnit, _HessianProxyUnits);
			return getProxyUnits(_HessianProxyUnits);
		}
		return proxyUnit;
	}
 
 
}
