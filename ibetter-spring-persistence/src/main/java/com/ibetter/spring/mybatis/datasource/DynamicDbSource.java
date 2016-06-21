package com.ibetter.spring.mybatis.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.ibetter.spring.mybatis.holder.DBContextHolder;

public class DynamicDbSource extends AbstractRoutingDataSource{
	protected final static Logger logger=LoggerFactory.getLogger(DynamicDbSource.class);
	@Override
	protected Object determineCurrentLookupKey() {
		String currentSource=DBContextHolder.getCurrentSource();
		if(logger.isDebugEnabled()){
			logger.debug("Current DataSource is ["+currentSource+"]");
		}
		return currentSource;
	}
}
