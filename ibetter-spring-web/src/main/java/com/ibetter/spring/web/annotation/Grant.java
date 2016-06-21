package com.ibetter.spring.web.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Grant {
	boolean force() default true;		//强制授权
	String[] properties() default {};	//授权验证属性	不设置属性将参数全签名
	@Deprecated
	boolean random() default true;		//随机数验证
	long time() default 1000*60*2;		//访问时间间隔控制
}
