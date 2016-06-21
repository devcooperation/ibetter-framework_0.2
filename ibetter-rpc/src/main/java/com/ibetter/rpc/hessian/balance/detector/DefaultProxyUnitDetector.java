/**
 * Project Name:ibetter-rpc
 * File Name:DefaultProxyUnitDetector.java
 * Copyright (c) 2016, www.zm0618.com All Rights Reserved.
 */
package com.ibetter.rpc.hessian.balance.detector;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibetter.rpc.hessian.balance.unit.ProxyStatus;
import com.ibetter.rpc.hessian.balance.unit.ProxyUnit;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年5月21日上午11:35:53</p>
 * <p>Description:TODO</p>
 */
public class DefaultProxyUnitDetector implements ProxyUnitDetector {
	
	protected final static Logger logger=LoggerFactory.getLogger(DefaultProxyUnitDetector.class);
	
	private List<ProxyUnit>  detectorList=new ArrayList<ProxyUnit>();
	
	private int second=30;
	private int detectSecond=30;
	
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(detectSecond*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (detectorList) {
						long currentMillis = System.currentTimeMillis();
						for (ProxyUnit hessianProxyUnit : detectorList) {
							long timestamp = hessianProxyUnit.getTimestamp();
							long timeGap=currentMillis-timestamp;
							if (timeGap>=second*1000) {
								if (ProxyStatus.PROXY_DOWN==hessianProxyUnit.getStatus()) {
									logger.debug("ProxyUnit->{},downtime->{}",hessianProxyUnit.getAccessPath(),String.valueOf(timestamp));
									hessianProxyUnit.setStatus(ProxyStatus.PROXY_UP);
								}
							}
						}
					}
				}
			}
		}).start();
		
	}
	@Override
	public void registProxy(ProxyUnit hessianProxyUnit) {
		synchronized (detectorList) {
			this.detectorList.add(hessianProxyUnit);
		}
	}
	
	
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}


	public int getDetectSecond() {
		return detectSecond;
	}


	public void setDetectSecond(int detectSecond) {
		this.detectSecond = detectSecond;
	}
	
	
}
