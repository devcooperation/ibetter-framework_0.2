/**
 * Project Name:ibetter-spring
 * File Name:RandomWeightProxySelector.java
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
 * <p>Date:2016年5月19日下午4:36:43</p>
 * <p>Description:TODO</p>
 */
public class RandomWeightProxySelector extends ProxyBaseSelector  implements ProxySelector {

	private List<ProxyUnit> proxyUnits=new ArrayList<ProxyUnit>();
	private Random random=new Random();
	
	@Override
	public void initProxys(List<ProxyUnit> proxyUnits) {
		for (ProxyUnit hessianProxyUnit : proxyUnits) {
			int weight = hessianProxyUnit.getWeight();
			if (weight>0) {
				for (int i = 0; i < weight; i++) {
					this.proxyUnits.add(hessianProxyUnit);
				}
			}
		}
	}

 
	@Override
	public ProxyUnit getProxyUnit()  throws NoAvailableProxyException{
		List<ProxyUnit> _weightUnitList=new ArrayList<ProxyUnit>();
		_weightUnitList.addAll(proxyUnits);
		ProxyUnit proxyUnit = this.getProxyUnit(_weightUnitList);
		proxyUnit.setTimestamp(System.currentTimeMillis());
		return proxyUnit;
	}

	
	public ProxyUnit getProxyUnit(List<ProxyUnit> _weightUnitList) throws NoAvailableProxyException{
		int _total=_weightUnitList.size();
		if (_total<=0) {
			throw new NoAvailableProxyException("All Proxies are down!");
		}
		int _c = this.random.nextInt(_total);
		ProxyUnit proxyUnit= _weightUnitList.get(_c);
		int status = proxyUnit.getStatus();
		if (status<=0) {
			removeProxyUnit(proxyUnit,_weightUnitList);
			return getProxyUnit(_weightUnitList);
		}
		return proxyUnit;
	}
	
 

 
 
}
