/**
 * Project Name:ibetter-spring
 * File Name:ProxyFactoryDelegate.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.remoting.proxy;

import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.SerializerFactory;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年4月9日下午1:30:55</p>
 * <p>Description:TODO</p>
 */
public class ProxyFactoryDelegate {
	
	private ClientConfig clientConfig;
	
	private HessianProxyFactory proxyFactory= new HessianProxyFactory();
	
	/**
	 *  <p>Author:zhaojun;</p>
	 *  <p>Date:2016年4月9日下午1:55:54;</p>
	 *	<p>Description: TODO;</p>
	 *  @param TODO 
	 *  @throws	TODO
	 */
	public ProxyFactoryDelegate(ClientConfig clientConfig) {
		super();
		this.clientConfig = clientConfig;
	}

	public void refresh(){
		
		this.setSerializerFactory(clientConfig.getSerializerFactory());
		long connectTimeout = clientConfig.getConnectTimeout();
		this.setConnectTimeout(connectTimeout);
		boolean sendCollectionType = clientConfig.isSendCollectionType();
		this.setSendCollectionType(sendCollectionType);
		this.setAllowNonSerializable(clientConfig.isAllowNonSerializable());
		this.setOverloadEnabled(clientConfig.isOverloadEnabled());
		this.setUsername(clientConfig.getUsername());
		this.setPassword(clientConfig.getPassword());
		this.setDebug(clientConfig.isDebug());
		this.setChunkedPost(clientConfig.isChunkedPost());
		HessianConnectionFactory connectionFactory=clientConfig.getConnectionFactory();
	    this.setConnectionFactory(connectionFactory);
		this.setConnectTimeout(clientConfig.connectTimeout);
		this.setReadTimeout(clientConfig.readTimeout);
	}
	/**
	 * Specify the Hessian SerializerFactory to use.
	 * <p>This will typically be passed in as an inner bean definition
	 * of type {@code com.caucho.hessian.io.SerializerFactory},
	 * with custom bean property values applied.
	 */
	public void setSerializerFactory(SerializerFactory serializerFactory) {
		this.proxyFactory.setSerializerFactory(serializerFactory);
	}

	/**
	 * Set whether to send the Java collection type for each serialized
	 * collection. Default is "true".
	 */
	public void setSendCollectionType(boolean sendCollectionType) {
		this.proxyFactory.getSerializerFactory().setSendCollectionType(sendCollectionType);
	}

	/**
	 * Set whether to allow non-serializable types as Hessian arguments
	 * and return values. Default is "true".
	 */
	public void setAllowNonSerializable(boolean allowNonSerializable) {
		this.proxyFactory.getSerializerFactory().setAllowNonSerializable(allowNonSerializable);
	}

	/**
	 * Set whether overloaded methods should be enabled for remote invocations.
	 * Default is "false".
	 * @see com.caucho.hessian.client.HessianProxyFactory#setOverloadEnabled
	 */
	public void setOverloadEnabled(boolean overloadEnabled) {
		this.proxyFactory.setOverloadEnabled(overloadEnabled);
	}

	/**
	 * Set the username that this factory should use to access the remote service.
	 * Default is none.
	 * <p>The username will be sent by Hessian via HTTP Basic Authentication.
	 * @see com.caucho.hessian.client.HessianProxyFactory#setUser
	 */
	public void setUsername(String username) {
		this.proxyFactory.setUser(username);
	}

	/**
	 * Set the password that this factory should use to access the remote service.
	 * Default is none.
	 * <p>The password will be sent by Hessian via HTTP Basic Authentication.
	 * @see com.caucho.hessian.client.HessianProxyFactory#setPassword
	 */
	public void setPassword(String password) {
		this.proxyFactory.setPassword(password);
	}

	/**
	 * Set whether Hessian's debug mode should be enabled.
	 * Default is "false".
	 * @see com.caucho.hessian.client.HessianProxyFactory#setDebug
	 */
	public void setDebug(boolean debug) {
		this.proxyFactory.setDebug(debug);
	}

	/**
	 * Set whether to use a chunked post for sending a Hessian request.
	 * @see com.caucho.hessian.client.HessianProxyFactory#setChunkedPost
	 */
	public void setChunkedPost(boolean chunkedPost) {
		this.proxyFactory.setChunkedPost(chunkedPost);
	}

	/**
	 * Specify a custom HessianConnectionFactory to use for the Hessian client.
	 */
	public void setConnectionFactory(HessianConnectionFactory connectionFactory) {
		this.proxyFactory.setConnectionFactory(connectionFactory);
	}

	/**
	 * Set the socket connect timeout to use for the Hessian client.
	 * @see com.caucho.hessian.client.HessianProxyFactory#setConnectTimeout
	 */
	public void setConnectTimeout(long timeout) {
		this.proxyFactory.setConnectTimeout(timeout);
	}

	/**
	 * Set the timeout to use when waiting for a reply from the Hessian service.
	 * @see com.caucho.hessian.client.HessianProxyFactory#setReadTimeout
	 */
	public void setReadTimeout(long timeout) {
		this.proxyFactory.setReadTimeout(timeout);
	}

	/**
	 * Set whether version 2 of the Hessian protocol should be used for
	 * parsing requests and replies. Default is "false".
	 * @see com.caucho.hessian.client.HessianProxyFactory#setHessian2Request
	 */
	public void setHessian2(boolean hessian2) {
		
		this.proxyFactory.setHessian2Request(hessian2);
		this.proxyFactory.setHessian2Reply(hessian2);
	}

	/**
	 * Set whether version 2 of the Hessian protocol should be used for
	 * parsing requests. Default is "false".
	 * @see com.caucho.hessian.client.HessianProxyFactory#setHessian2Request
	 */
	public void setHessian2Request(boolean hessian2) {
		this.proxyFactory.setHessian2Request(hessian2);
	}

	/**
	 * Set whether version 2 of the Hessian protocol should be used for
	 * parsing replies. Default is "false".
	 * @see com.caucho.hessian.client.HessianProxyFactory#setHessian2Reply
	 */
	public void setHessian2Reply(boolean hessian2) {
		this.proxyFactory.setHessian2Reply(hessian2);
	}

	/**
	 * @return the clientConfig
	 */
	public ClientConfig getClientConfig() {
		return clientConfig;
	}

	/** 
	* @param clientConfig clientConfig 
	*/
	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	/**
	 * @return the proxyFactory
	 */
	public HessianProxyFactory getProxyFactory() {
		return proxyFactory;
	}
	
}
