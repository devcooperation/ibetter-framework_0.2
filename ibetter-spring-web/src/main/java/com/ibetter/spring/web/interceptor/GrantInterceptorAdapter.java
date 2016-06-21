/**
 * Project Name:ibetter-spring
 * File Name:GrantHandlerInterceptorAdapter.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.spring.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ibetter.spring.web.annotation.Grant;
import com.ibetter.spring.web.servlet.RequestDelegate;
import com.ibetter.spring.web.token.DefaultTokenRepo;
import com.ibetter.spring.web.token.TokenRepository;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月15日下午12:22:25</p>
 * <p>Description:TODO</p>
 */
public class GrantInterceptorAdapter extends HandlerInterceptorAdapter {
	private boolean skipVerify;
	
 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		RequestDelegate requestDelegate = RequestDelegate.create(request);
		//
		Class<?> clazz = ClassUtils.getUserClass(handler);
		if(clazz==HandlerMethod.class){
			
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Object controllerBean = handlerMethod.getBean();
			Class<? extends Object> controllerclazz = controllerBean.getClass();
			
			Grant controllerGrant=controllerclazz.getDeclaredAnnotation(Grant.class);
			Grant methodGrant = handlerMethod.getMethodAnnotation(Grant.class);
			if(controllerGrant!=null&&controllerGrant.force()){
				//全部强制授权
				if(methodGrant!=null&&methodGrant.force()){
					String[] properties = methodGrant.properties();
					if(ArrayUtils.getLength(properties)>0){
						return this.verify(requestDelegate, properties);//按指定属性签名
					}else{
						return this.verify(requestDelegate);//全部签名
					}
				}else{//全部签名
					return this.verify(requestDelegate);
				}
			}else{
				if(controllerGrant==null){
					if(methodGrant!=null&&methodGrant.force()){//签名当个方法
						String[] properties = methodGrant.properties();
						if(ArrayUtils.getLength(properties)>0){
							return this.verify(requestDelegate, properties);//按指定属性签名
						}else{
							//全部签名
							return this.verify(requestDelegate);
						}
					}
				}
				
			}
			
			
		}
		return super.preHandle(request, response, handler);
	}
	
	public boolean verify(RequestDelegate requestDelegate,String... properties){
		return false;
	}

	public boolean isSkipVerify() {
		return skipVerify;
	}

	public void setSkipVerify(boolean skipVerify) {
		this.skipVerify = skipVerify;
	}

}
