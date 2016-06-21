/**
 * Project Name:ibetter-rpc
 * File Name:ProxyUnitStatBase.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.selector;

import java.util.List;

import com.ibetter.rpc.hessian.balance.exception.NoAvailableProxyException;
import com.ibetter.rpc.hessian.balance.unit.ProxyStatus;
import com.ibetter.rpc.hessian.balance.unit.ProxyUnit;

 
public abstract class ProxyBaseSelector implements ProxySelector {
 
	@Override
	public abstract void initProxys(List<ProxyUnit> proxyUnits);

	 
	@Override
	public abstract  ProxyUnit getProxyUnit() throws NoAvailableProxyException ;

	 
	@Override
	public void changeStatus(ProxyStatus proxyStatus) {
		ProxyUnit proxyUnit = proxyStatus.getProxyUnit();
		int status=proxyStatus.getStatus();
		proxyUnit.setStatus(status);
	}
	public void removeProxyUnit(ProxyUnit proxyUnit,List<ProxyUnit> _proxyUnitList) {
		for (ProxyUnit _proxyUnit : _proxyUnitList) {
			if(_proxyUnit==proxyUnit){
				_proxyUnitList.remove(_proxyUnit);
				removeProxyUnit(_proxyUnit, _proxyUnitList);
				break;
			}
		}
	}
 
}
