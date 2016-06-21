/**
 * Project Name:ibetter-spring-web
 * File Name:DefaultMappingJackson2HttpMessageWrapper.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.spring.http.converter.json;

import com.ibetter.spring.http.converter.wrapper.ConvertWrapper;
import com.ibetter.spring.web.servlet.RemoteResult;
import com.ibetter.spring.web.servlet.ResultUtils;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年6月17日下午4:47:23</p>
 * <p>Description:TODO</p>
 */
public class BaseMappingJackson2HttpMessageWrapper implements ConvertWrapper {
	
	@Override
	public Object wrap(Object object) {
		
		beforeWrap(object);
		//
		RemoteResult resultObj = processWrap(object);
		//
		afterWrap(resultObj);
		
		return refresh(resultObj);
	}
 
	 
	public Object refresh(RemoteResult resultObj) {
		return resultObj;
	}

	public void afterWrap(RemoteResult resultObj) {}


	public void beforeWrap(Object object) {}

	
	private RemoteResult processWrap(Object object) {
		RemoteResult remoteResult=null;
		if(object == null){
			remoteResult=ResultUtils.createNullResult();
		}
		if(object instanceof RemoteResult){
			remoteResult= (RemoteResult)object;
		}else{
			remoteResult= ResultUtils.createDefResult(object);
		}
		return remoteResult;
	}

}
