package com.ibetter.spring.mybatis.query.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 返回ID结果注解
 * @author zhaojun
 * 2016年1月22日 下午6:16:51
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IdResult {
	String property() default "id";	//id属性名称
}
