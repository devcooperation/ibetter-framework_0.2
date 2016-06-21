/**
 * Project Name:ibetter-spring
 * File Name:WeightProxySelector.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.selector;

import java.util.ArrayList;
import java.util.List;

import com.ibetter.rpc.hessian.balance.unit.ProxyUnit;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月19日下午4:27:37</p>
 * <p>Description:权重</p>
 */
public class WeightProxySelector extends RoundProxySelector implements ProxySelector {

	@Override
	public void initProxys(List<ProxyUnit> _proxyUnits) {
		this.proxyUnits=new ArrayList<ProxyUnit>();
		for (ProxyUnit hessianProxyUnit : _proxyUnits) {
			int weight = hessianProxyUnit.getWeight();
			if (weight>0) {
				for (int i = 0; i < weight; i++) {
					this.proxyUnits.add(hessianProxyUnit);
				}
			}
			
		}
		total=this.proxyUnits.size();
	}
}
