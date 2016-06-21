package com.ibetter.spring.mybatis.query.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 缓存注解
 * @author zhaojun
 * 2016年1月22日 下午6:16:37
 */
@Retention(RetentionPolicy.RUNTIME)

public @interface DLCache {
	/**
	 * 缓存生命周期
	 * @return long
	 */
	long ttl() default -1;
	
	/**
	 * 设置过期时间 一天中的某个时间点过期
	 *  <p>Author:zhaojun;</p>
	 *  <p>Date:2016年4月1日下午1:55:46;</p>
	 *	<p>Description: TODO;</p>
	 *  @return String    
	 */
	String time() default "";
	
	boolean evictable() default false;
}
