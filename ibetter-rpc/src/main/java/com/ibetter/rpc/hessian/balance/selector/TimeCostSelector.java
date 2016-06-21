/**
 * Project Name:ibetter-rpc
 * File Name:MinTimeCostSelector.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.selector;

import java.util.ArrayList;
import java.util.List;

import com.ibetter.rpc.hessian.balance.exception.NoAvailableProxyException;
import com.ibetter.rpc.hessian.balance.unit.ProxyStatus;
import com.ibetter.rpc.hessian.balance.unit.ProxyTimeCostStatus;
import com.ibetter.rpc.hessian.balance.unit.ProxyUnit;
import com.ibetter.rpc.hessian.balance.unit.TProxyUnit;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月23日下午1:41:59</p>
 * <p>Description:TODO</p>
 */
public class TimeCostSelector extends ProxyBaseSelector implements ProxySelector {
	private List<TProxyUnit> proxyUnits=new ArrayList<TProxyUnit>();
	

 
	@Override
	public void initProxys(List<ProxyUnit> proxyUnits) {
		for (ProxyUnit hessianProxyUnit : proxyUnits) {
			TProxyUnit proxyUnit=new TProxyUnit(hessianProxyUnit);
			this.proxyUnits.add(proxyUnit);
		}

	}
 
	@Override
	public ProxyUnit getProxyUnit() throws NoAvailableProxyException {
		List<TProxyUnit> _proxyUnits=new ArrayList<TProxyUnit>();
		_proxyUnits.addAll(proxyUnits);
		return getProxyUnit(_proxyUnits);
	}
 
 
	private ProxyUnit getProxyUnit(List<TProxyUnit> _proxyUnits) {
		TProxyUnit _tProxyUnit=null;
		long minTime=Long.MAX_VALUE;
		for (TProxyUnit tProxyUnit : _proxyUnits) {
			long _proxyTimeCost=tProxyUnit.getTimecost();
			if (tProxyUnit.getStatus()>0&&_proxyTimeCost<=minTime) {
				minTime=_proxyTimeCost;
				_tProxyUnit=tProxyUnit;
			}else{
				if (tProxyUnit.getStatus()<=0) {
					_proxyUnits.remove(tProxyUnit);
					return getProxyUnit(_proxyUnits);
				}
			}
		}
		return _tProxyUnit;
	}

	@Override
	public void changeStatus(ProxyStatus proxyStatus) {
		super.changeStatus(proxyStatus);
		TProxyUnit _proxyUnit = (TProxyUnit) proxyStatus.getProxyUnit();
		ProxyTimeCostStatus _proxyStatus=(ProxyTimeCostStatus) proxyStatus;
		_proxyUnit.setTimecost(_proxyStatus.getTimeCost());
	}

}
