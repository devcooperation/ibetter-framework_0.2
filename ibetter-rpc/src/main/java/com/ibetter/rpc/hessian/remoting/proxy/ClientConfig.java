/**
 * Project Name:ibetter-spring
 * File Name:ClientProperites.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.remoting.proxy;

import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.io.SerializerFactory;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年4月9日下午1:37:33</p>
 * <p>Description:TODO</p>
 */
public class ClientConfig {
	
	
	private String host;
	
	private SerializerFactory serializerFactory;
	
	private boolean sendCollectionType=true;
	
	private boolean allowNonSerializable=true;
	
	private boolean overloadEnabled;
	
	private String username;
	
	private String password;
	
	private boolean debug;
	
	private boolean chunkedPost=true;
	
	private HessianConnectionFactory connectionFactory;
	
	private int weight;//访问权重
	
	long connectTimeout=-1;
	
	long readTimeout=-1;
	
	public boolean hessian2;

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/** 
	* @param host host 
	*/
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the serializerFactory
	 */
	public SerializerFactory getSerializerFactory() {
		return serializerFactory;
	}

	/** 
	* @param serializerFactory serializerFactory 
	*/
	public void setSerializerFactory(SerializerFactory serializerFactory) {
		this.serializerFactory = serializerFactory;
	}

	/**
	 * @return the sendCollectionType
	 */
	public boolean isSendCollectionType() {
		return sendCollectionType;
	}

	/** 
	* @param sendCollectionType sendCollectionType 
	*/
	public void setSendCollectionType(boolean sendCollectionType) {
		this.sendCollectionType = sendCollectionType;
	}

	/**
	 * @return the allowNonSerializable
	 */
	public boolean isAllowNonSerializable() {
		return allowNonSerializable;
	}

	/** 
	* @param allowNonSerializable allowNonSerializable 
	*/
	public void setAllowNonSerializable(boolean allowNonSerializable) {
		this.allowNonSerializable = allowNonSerializable;
	}

	/**
	 * @return the overloadEnabled
	 */
	public boolean isOverloadEnabled() {
		return overloadEnabled;
	}

	/** 
	* @param overloadEnabled overloadEnabled 
	*/
	public void setOverloadEnabled(boolean overloadEnabled) {
		this.overloadEnabled = overloadEnabled;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/** 
	* @param username username 
	*/
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/** 
	* @param password password 
	*/
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/** 
	* @param debug debug 
	*/
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the chunkedPost
	 */
	public boolean isChunkedPost() {
		return chunkedPost;
	}

	/** 
	* @param chunkedPost chunkedPost 
	*/
	public void setChunkedPost(boolean chunkedPost) {
		this.chunkedPost = chunkedPost;
	}

	/**
	 * @return the connectionFactory
	 */
	public HessianConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/** 
	* @param connectionFactory connectionFactory 
	*/
	public void setConnectionFactory(HessianConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	/**
	 * @return the connectTimeout
	 */
	public long getConnectTimeout() {
		return connectTimeout;
	}

	/** 
	* @param connectTimeout connectTimeout 
	*/
	public void setConnectTimeout(long connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the readTimeout
	 */
	public long getReadTimeout() {
		return readTimeout;
	}

	/** 
	* @param readTimeout readTimeout 
	*/
	public void setReadTimeout(long readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

 
	
}