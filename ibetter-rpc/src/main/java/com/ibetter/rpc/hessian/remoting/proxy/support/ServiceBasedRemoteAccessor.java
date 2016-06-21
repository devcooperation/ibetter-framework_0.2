/**
 * Project Name:ibetter-rpc
 * File Name:FacadeBasedRemoteAccessor.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.remoting.proxy.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteAccessor;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月20日下午6:10:39</p>
 * <p>Description:TODO</p>
 */
public abstract class ServiceBasedRemoteAccessor extends RemoteAccessor implements InitializingBean {
	private String serviceName;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Override
	public void afterPropertiesSet() {
	}
}
