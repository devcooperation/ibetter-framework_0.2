/**
 * Project Name:ibetter-spring
 * File Name:GrantAccessInterceptor.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.spring.web.interceptor;

import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.ibetter.common.util.MD5Util;
import com.ibetter.spring.web.exception.GrantException;
import com.ibetter.spring.web.servlet.RequestDelegate;
import com.ibetter.spring.web.token.DefaultTokenRepo;
import com.ibetter.spring.web.token.TokenBind;
import com.ibetter.spring.web.token.TokenRepository;
import com.ibetter.spring.web.verifyer.SignParamUtil;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月15日下午12:24:45</p>
 * <p>Description:TODO</p>
 */
public class GrantAccessInterceptor extends GrantInterceptorAdapter {
	
	private TokenRepository tokenRepository=new DefaultTokenRepo();	//默认token仓库

	protected final static String REQUEST_SIGN = "sign";
	protected final static String REQUEST_TIMESTAMP = "timestamp";
	protected final static String REQUEST_TOKEN = "accessToken";
	protected final static String APPKEY = "appKey";
	
	@Override
	public boolean verify(RequestDelegate requestDelegate, String... properties) {
		
		if (!this.isSkipVerify()) {
			String sign=requestDelegate.getHeader(REQUEST_SIGN);//获取签名
			String token=requestDelegate.getHeader(REQUEST_TOKEN);//TOKEN
			String timestamp=requestDelegate.getHeader(REQUEST_TIMESTAMP);//时间戳
			validate(sign);
			TokenBind tokenBind=tokenRepository.retriveTokenBind(token);
			if(tokenBind==null){
				throw new GrantException(String.format("Token[%s] is error!", token));
			}
			TreeMap<String, Object> paramMap = SignParamUtil.getSignParam(requestDelegate,properties);
			paramMap.put(REQUEST_TIMESTAMP, timestamp);
			paramMap.put(APPKEY, tokenBind.getAppKey());
			paramMap.put(REQUEST_TOKEN, token);
			String paramQueryStr=SignParamUtil.flat(paramMap); 
			String queryMd5=MD5Util.MD5(paramQueryStr);
			return StringUtils.equalsIgnoreCase(queryMd5, sign);
		}
		return true;
	}


	private void validate(String sign) {
		if (StringUtils.isBlank(sign)) {
			throw new GrantException(String.format("Sign[%s] is error!", sign));
		}
	}


	public void setTokenRepository(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}
	
	
	
}
