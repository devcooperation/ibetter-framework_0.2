package com.ibetter.spring.mybatis.query.advice;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.ibetter.spring.mybatis.holder.DBContextHolder;
import com.ibetter.spring.mybatis.holder.StmtContextHolder;
import com.ibetter.spring.mybatis.query.annotation.DLCache;
import com.ibetter.spring.mybatis.query.annotation.IdResult;
import com.ibetter.spring.mybatis.query.annotation.QBind;
import com.ibetter.spring.mybatis.query.annotation.QRoute;
import com.ibetter.spring.mybatis.query.cache.CacheKey;
import com.ibetter.spring.mybatis.query.cache.QueryCache;

public class MyBatisQContextAdvice {
	
	protected final static Logger logger=LoggerFactory.getLogger(MyBatisQContextAdvice.class);
	
	private QueryCache queryCache;
	
	public Object execute(ProceedingJoinPoint call) throws Throwable {
		
		
		Object rtv=null;
		
		Object target = call.getTarget();
		
		MethodSignature signature = (MethodSignature) call.getSignature();
		
		Object[] args = call.getArgs();
		
		Class<?>[] clazz = findInterfaces(target);
		
		String typeName=clazz[0].getTypeName();

		Method method = signature.getMethod();
		
		initQRoute(method);
		
		String statement=createStmt(method,typeName);
		
		if(queryCache!=null){//缓存拦截
			
			if(method.isAnnotationPresent(DLCache.class)){
				DLCache dlCache=method.getAnnotation(DLCache.class);
				Class<?> cacheType = method.getReturnType();
				CacheKey cacheKey = new CacheKey(statement,args);
				rtv=queryCache.getFromCache(cacheKey,cacheType);
				
				if(rtv==null){
					rtv = call.proceed(args);
					long ttl = dlCache.ttl();
					String time=dlCache.time();
					
					cacheKey.setTtl(ttl);
					cacheKey.setTime(time);
					
					queryCache.putCache(cacheKey,rtv);
				}else{
					if(logger.isDebugEnabled()){
						logger.debug("[{}] cache hit", statement);
					}
				}
			}else{
				rtv = call.proceed(args);
			}
		}else{
			rtv = call.proceed(args);
		}
	    return rtv!=null?convertResult(method,args,rtv):null;
	}

	private String createStmt(Method method, String typeName) {
		String alias=getQBind(method);
		String methodName=(alias==null)?method.getName():alias;
		StmtContextHolder.initStmtContext(typeName);
		String statement = StmtContextHolder.createStmt(methodName);
		StmtContextHolder.initStmt(statement);
		if(logger.isDebugEnabled()){
			logger.debug("init statement [{}] ",statement);
		}
		return statement;
	}

	private String getQBind(Method method) {
		
		if (method.isAnnotationPresent(QBind.class)) {
			QBind qAlias=method.getAnnotation(QBind.class);
			String aliasValue=qAlias.value();
			if(StringUtils.isNotBlank(aliasValue)){
				return aliasValue;
			}
		}
		return null;
	}

	private Object convertResult(Method method,Object[] args, Object rtv) {
		if(method.isAnnotationPresent(IdResult.class)){
			IdResult idResult = method.getAnnotation(IdResult.class);
			String idProperty=idResult.property();
			Object insertParam=args[0];
			Class<?> rzClass=insertParam.getClass();
			Field idField = ReflectionUtils.findField(rzClass, idProperty);
			ReflectionUtils.makeAccessible(idField); 
			try {
				return idField.get(insertParam);
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return rtv;
	}

	private Class<?>[] findInterfaces(Object target) {
		Class<?>[] clazz = target.getClass().getInterfaces();
		if(clazz.length==0){
			throw new RuntimeException("The class does not implement interface!");
		}
		return clazz;
	}

	private void initQRoute(Method method) {
		if(method.isAnnotationPresent(QRoute.class)){
			QRoute qroute=method.getAnnotation(QRoute.class);
			String dbsource=qroute.value();
			if(logger.isDebugEnabled()){
				logger.debug("Current dbSource-->"+dbsource);
			}
			DBContextHolder.initDb(dbsource);
		}
	}

	public void setQueryCache(QueryCache queryCache) {
		this.queryCache = queryCache;
	}
	
}
