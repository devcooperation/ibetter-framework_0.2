package com.ibetter.spring.mybatis.query.cache;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.ibetter.common.util.MD5Util;

public class CacheKey {
	
	private String statement;
	private Object[] params;
	private long ttl;//设置生存周期 定时过期
	private String time;//设置过期时间 每一天的时间点
	
	public CacheKey() {
		super();
	}
	public CacheKey(String statement, Object[] params) {
		super();
		this.statement = statement;
		this.params = params;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}
	public long getTtl() {
		return ttl;
	}
	public void setTtl(long ttl) {
		this.ttl = ttl;
	}
	
 
	public String getTime() {
		return time;
	}
	 
	public void setTime(String time) {
		this.time = time;
	}
	public String keyName(){//生成KEY名称
		String paramSerialHash=paramHash();
		if(StringUtils.isNotBlank(paramSerialHash)){
			return StringUtils.join("cache:",statement,":",paramSerialHash); 
		}
		return StringUtils.join("cache:",statement);
	}
	
	 
	private String paramHash() {
		if (ArrayUtils.isNotEmpty(params)) {
			StringBuilder sBuilder=new StringBuilder();
			for (int i = 0; i < params.length; i++) {
				Class<? extends Object> paramClazz = params[i].getClass();
				
				sBuilder.append(paramClazz.getName()).append(String.valueOf(params[i]));
			}
			return MD5Util.MD5(sBuilder.toString());
		}
		return "";
	}
	
	 
	
	
}
