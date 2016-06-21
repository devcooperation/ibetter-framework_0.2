/**
 * Project Name:ibetter-spring
 * File Name:HessianClientInterceptor.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.remoting.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.util.Assert;

import com.caucho.hessian.HessianException;
import com.caucho.hessian.client.HessianConnectionException;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import com.ibetter.rpc.hessian.balance.detector.ProxyUnitDetector;
import com.ibetter.rpc.hessian.balance.exception.NoAvailableProxyException;
import com.ibetter.rpc.hessian.balance.selector.TimeCostSelector;
import com.ibetter.rpc.hessian.balance.selector.ProxySelector;
import com.ibetter.rpc.hessian.balance.selector.RandomProxySelector;
import com.ibetter.rpc.hessian.balance.selector.RandomWeightProxySelector;
import com.ibetter.rpc.hessian.balance.selector.RoundProxySelector;
import com.ibetter.rpc.hessian.balance.selector.WeightProxySelector;
import com.ibetter.rpc.hessian.balance.unit.ProxyStatus;
import com.ibetter.rpc.hessian.balance.unit.ProxyTimeCostStatus;
import com.ibetter.rpc.hessian.balance.unit.ProxyUnit;
import com.ibetter.rpc.hessian.balance.unit.TProxyUnit;
import com.ibetter.rpc.hessian.remoting.proxy.support.ServiceBasedRemoteAccessor;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年4月9日下午1:18:17</p>
 * <p>Description:TODO</p>
 */
public class HessianClientInterceptor extends ServiceBasedRemoteAccessor implements MethodInterceptor{
	
	protected Logger logger=LoggerFactory.getLogger(HessianClientInterceptor.class);
	
	protected ClientConfigs clientConfigs;
	
	protected List<ProxyFactoryDelegate> proxyFactoryDelegateList=new ArrayList<ProxyFactoryDelegate>();
	
	protected List<Object> hessianProxyList=new ArrayList<Object>();
	
	protected ProxySelector proxySelector;
	
	protected Map<String,ProxySelector> selectors= new HashMap<String, ProxySelector>();
	
	protected String balance;
	
	protected int proxySelectRetry;
	
	
	public ProxyUnit getHessianProxyUnit(int retryTime) throws ConnectException{
		ProxyUnit proxyUnit = null;
		try {
			proxyUnit = proxySelector.getProxyUnit();
		} catch (NoAvailableProxyException e) {
			 logger.error(e.getMessage());
			 retryTime--;
			 if (retryTime>=0) {
				 return getHessianProxyUnit(retryTime);
			 }else{
				 throw new ConnectException("Failed to invoke Hessian proxy for remote service [" + getServiceName() + "]");
			 }
		}
		return proxyUnit;
	} 
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
	
		
		ClassLoader originalClassLoader = overrideThreadContextClassLoader();
		ProxyUnit proxyUnit = null;
		long timeCost=0;
		
		try {
			proxyUnit=getHessianProxyUnit(proxySelectRetry);
			logger.debug("{}",proxyUnit.toString());
			Object hessianProxy=proxyUnit.getProxyObject();
			long startTime=System.currentTimeMillis();
			Object rtn = invocation.getMethod().invoke(hessianProxy, invocation.getArguments());
			long endTime=System.currentTimeMillis();
			timeCost=endTime-startTime;
			logger.debug("{},timeCost:{}",proxyUnit.toString(),timeCost);
			if(proxyUnit instanceof TProxyUnit){
				this.proxySelector.changeStatus(new ProxyTimeCostStatus(proxyUnit, proxyUnit.getStatus(),timeCost));
			}
			return rtn;
		}
		catch (InvocationTargetException ex) {
			
			Throwable targetEx = ex.getTargetException();
			logger.debug(ex.getMessage());
			if (targetEx instanceof InvocationTargetException) {
				targetEx = ((InvocationTargetException) targetEx).getTargetException();
			}
			if (targetEx instanceof HessianConnectionException) {
				forceProxyDown(proxyUnit, timeCost);
				Object rtn=this.invoke(invocation);
				if (rtn!=null) {
					return rtn;
				}
				throw convertHessianAccessException(targetEx);
			}
			else if(targetEx instanceof HessianRuntimeException){
				forceProxyDown(proxyUnit, timeCost);
				Object rtn=this.invoke(invocation);
				if (rtn!=null) {
					return rtn;
				}
				throw convertHessianAccessException(targetEx);
			}
			else if (targetEx instanceof HessianException) {
				Throwable cause = targetEx.getCause();
				throw convertHessianAccessException(cause);
			}
			else if (targetEx instanceof UndeclaredThrowableException) {
				UndeclaredThrowableException utex = (UndeclaredThrowableException) targetEx;
				throw convertHessianAccessException(utex.getUndeclaredThrowable());
			}
			else {
				throw targetEx;
			}
		}
		catch (ConnectException ex) {
			this.forceProxyDown(proxyUnit, timeCost);
		    return this.invoke(invocation);
			
		}catch(Throwable ex){
			throw new RemoteProxyFailureException(
					"Failed to invoke Hessian proxy for remote service [" + getServiceName() + "]", ex);
			
		}
		finally {
		
			resetThreadContextClassLoader(originalClassLoader);
			if (proxyUnit!=null) {
				proxyUnit.setTimestamp(System.currentTimeMillis());
			}
			
		}
	}


	private void forceProxyDown(ProxyUnit proxyUnit, long timeCost) {
		if (proxyUnit!=null) {
			if(proxyUnit instanceof TProxyUnit){
				this.proxySelector.changeStatus(new ProxyTimeCostStatus(proxyUnit, ProxyStatus.PROXY_DOWN,timeCost));
			}else{
				ProxyStatus proxyStatus = new ProxyStatus(proxyUnit, ProxyStatus.PROXY_DOWN);
				this.proxySelector.changeStatus(proxyStatus);
				logger.info("{} is down",String.valueOf(proxyUnit));
			}
		}
	}
	
	
	protected RemoteAccessException convertHessianAccessException(Throwable ex) {
		if (ex instanceof HessianConnectionException || ex instanceof ConnectException) {
			return new RemoteConnectFailureException(
					"Cannot connect to Hessian remote service at [" + getServiceName() + "]", ex);
		}
		else {
			return new RemoteAccessException(
				"Cannot access Hessian remote service at [" + getServiceName() + "]", ex);
		}
	}
	 
	
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		this.prepare();
	}
	
	public void prepare() throws RemoteLookupFailureException {
		registProxySelector();
		createProxyFactory();
		createHessianProxy();
		setProxySelectRetry();
	}
	
	 
 
	protected void setProxySelectRetry() {
		if (this.proxySelectRetry<=0) {
			this.proxySelectRetry=this.clientConfigs.getProxySelectRetry();
		}
	}

	protected void registProxySelector() {
		selectors.put(ProxySelector._random, new RandomProxySelector());
		selectors.put(ProxySelector._random_weight, new RandomWeightProxySelector());
		selectors.put(ProxySelector._round, new RoundProxySelector());
		selectors.put(ProxySelector._weight, new WeightProxySelector());
		selectors.put(ProxySelector._time_cost, new TimeCostSelector());
	}
	protected Object createHessianProxy(HessianProxyFactory proxyFactory,String serviceUrl) throws MalformedURLException {
		Assert.notNull(getServiceInterface(), "'serviceInterface' is required");
		return proxyFactory.create(getServiceInterface(), serviceUrl, getBeanClassLoader());
	}
	
	protected ProxyUnit createHessianProxyUnit(ProxyFactoryDelegate proxyFactoryDelegate) throws MalformedURLException {
		Assert.notNull(getServiceInterface(), "'serviceInterface' is required");
		
		HessianProxyFactory proxyFactory=proxyFactoryDelegate.getProxyFactory();
		String host=proxyFactoryDelegate.getClientConfig().getHost();
		
		String serviceName=getServiceName();
		
		if (serviceName==null) {
			Class<?> serviceInterface = getServiceInterface();
			String simpleName=serviceInterface.getSimpleName();
			serviceName=StringUtils.join(StringUtils.lowerCase(simpleName.substring(0, 1)),simpleName.substring(1));
			this.setServiceName(serviceName);
		}
		String accessServiceUrl=StringUtils.join(host,(StringUtils.endsWith(host, "/")?"":"/"), getServiceName());
		
		Object proxyObject=this.createHessianProxy(proxyFactory,accessServiceUrl);
		
		ProxyUnit proxyUnit=new ProxyUnit();
		proxyUnit.setProxyObject(proxyObject);
		proxyUnit.setAccessPath(accessServiceUrl);
		proxyUnit.setStatus(1);
		ClientConfig clientConfig = proxyFactoryDelegate.getClientConfig();
		proxyUnit.setWeight(clientConfig.getWeight());
		proxyUnit.setTimestamp(System.currentTimeMillis());
		
		return proxyUnit;
	}
	

	 
	private void createHessianProxy() {
		try {
			ProxyUnitDetector proxyDetector = this.clientConfigs.getProxyDetector();
			ProxySelector _proxySelector = selectors.get(this.balance==null?this.clientConfigs.getBalance():this.balance);
			List<ProxyUnit>proxyUnitList=new ArrayList<ProxyUnit>();
			for (ProxyFactoryDelegate proxyFactoryDelegate : proxyFactoryDelegateList) {
				  ProxyUnit proxyUnit = this.createHessianProxyUnit(proxyFactoryDelegate);
				  proxyUnitList.add(proxyUnit);
				  proxyDetector.registProxy(proxyUnit);
			}
			_proxySelector.initProxys(proxyUnitList);
			
			this.proxySelector=_proxySelector;
		} catch (MalformedURLException ex) {
			throw new RemoteLookupFailureException("Service URL [" + getServiceName() + "] is invalid", ex);
		}
	}

	 
	private void createProxyFactory() {
		for (ClientConfig clientConfig : clientConfigs.getConfigs()) {
			ProxyFactoryDelegate proxyFactoryDelegate=new ProxyFactoryDelegate(clientConfig);
			proxyFactoryDelegate.refresh();
			proxyFactoryDelegateList.add(proxyFactoryDelegate);
		}
	}
	
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}

	public ClientConfigs getClientConfigs() {
		return clientConfigs;
	}

	public void setClientConfigs(ClientConfigs clientConfigs) {
		this.clientConfigs = clientConfigs;
	}

	public int getProxySelectRetry() {
		return proxySelectRetry;
	}

	public void setProxySelectRetry(int proxySelectRetry) {
		this.proxySelectRetry = proxySelectRetry;
	}





}
