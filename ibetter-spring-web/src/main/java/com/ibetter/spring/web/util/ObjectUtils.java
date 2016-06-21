package com.ibetter.spring.web.util;

import com.ibetter.spring.web.servlet.Null;

public class ObjectUtils {
	public static Null newNull(){
		return new Null();
	}
	public static Null singleNull(){
		return Null.NULL;
	}
}
