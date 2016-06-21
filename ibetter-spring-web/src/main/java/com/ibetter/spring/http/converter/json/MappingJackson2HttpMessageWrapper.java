package com.ibetter.spring.http.converter.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibetter.common.util.ProcessContextManagerUtil;
import com.ibetter.spring.http.converter.wrapper.ConvertWrapper;
import com.ibetter.spring.web.servlet.RemoteResult;
import com.ibetter.spring.web.servlet.ResultStatus;
import com.ibetter.spring.web.servlet.ResultUtils;

public class MappingJackson2HttpMessageWrapper implements ConvertWrapper {

	protected  final Logger logger=LoggerFactory.getLogger(MappingJackson2HttpMessageWrapper.class);
	@Override
	public Object wrap(Object object) {
		if(object instanceof RemoteResult&&!String.valueOf(((RemoteResult) object).getStatus()).startsWith(String.valueOf(ResultStatus.SUCCESS))){
			return object;
		}
		Object result =  ProcessContextManagerUtil.getAttribute("_REMOTERESULT");
		if(object == null){
			if(result !=null){
				return result;
			}
			return ResultUtils.createNullResult();
		}else if(object instanceof RemoteResult){
			if(result !=null){
				RemoteResult remoteResult = (RemoteResult)result;
				RemoteResult remoteResultObject = (RemoteResult)object;
				remoteResult.setData(remoteResultObject.getData());
				return remoteResult;
			}
			return object;
		}else{
			if(result !=null){
				RemoteResult remoteResult = (RemoteResult)result;
				remoteResult.setData(object);
				return remoteResult;
			}
			return ResultUtils.createDefResult(object);
		}
		
		
	}
	
}
