package com.ibetter.rpc.facade.monitor;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.ibetter.rpc.biz.exception.BizExecption;
import com.ibetter.rpc.common.exception.SysInnerException;
import com.ibetter.rpc.facade.exception.ParamException;

public class FacadeMonitor {

	protected final Logger logger = LoggerFactory.getLogger(FacadeMonitor.class);

	protected Class<?> bizExceptionClazz;//业务异常抛出转换
	
	
	public Object execute(ProceedingJoinPoint call) throws Throwable {

		Object obj = null;
		Object target = call.getTarget();
		Class<? extends Object> clzz = target.getClass();
		MethodSignature signature = (MethodSignature) call.getSignature();
		Method method = signature.getMethod();
		try {
			logger.debug("######{}.{}########",clzz.getName(),method.getName());
			obj = call.proceed();
		} catch (Exception e) {
		 	logger.error("********************{}.{}************",clzz.getName(),method.getName());
			logger.error(e.getMessage(), e);
		
			Class<?> execptionClazz = e.getClass();
			if (execptionClazz == ParamException.class) {
				throw e;
			}
			if (bizExceptionClazz!=null && execptionClazz == bizExceptionClazz) {
				throw new BizExecption(e.getMessage());
			}
			if(execptionClazz == BizExecption.class){
				throw e;
			}
			throw new SysInnerException("SYSTEM-ERROR");
		}
		return obj;
	}

	public void setBizExeptionClazz(String clzz) {
		try {
			this.bizExceptionClazz = ClassUtils.forName(clzz, null);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (LinkageError e) {
			logger.error(e.getMessage(), e);
		}
	}
}
