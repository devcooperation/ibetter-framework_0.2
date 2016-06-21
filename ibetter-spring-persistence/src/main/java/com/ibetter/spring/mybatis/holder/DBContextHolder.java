package com.ibetter.spring.mybatis.holder;


public class DBContextHolder {
	
	public static final ThreadLocal<String> holdThreadLocal=new ThreadLocal<String>();
	
	public static void initDb(String source){
		holdThreadLocal.set(source);
	}
	public static String getCurrentSource(){
		String dbSource=holdThreadLocal.get();
		return dbSource;
	}
}
