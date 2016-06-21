/**
 * Project Name:ibetter-spring
 * File Name:RedisSupport.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.spring.redis.query.support;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月18日上午10:16:59</p>
 * <p>Description:TODO</p>
 */

@Component
public class RedisSupport {
	
	private static final String charset = "utf-8";
	@Autowired protected RedisTemplate<String,String> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String,String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public RedisTemplate<String,String> getRedisTemplate() {
		return redisTemplate;
	}
	
	
	public byte[] toBytes(Object o){
		try {
			String str=o.toString();
			if(StringUtils.isNotBlank(str)){
				return str.getBytes(charset);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String bytesToString( byte[] b){
		try {
			return new String(b, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
