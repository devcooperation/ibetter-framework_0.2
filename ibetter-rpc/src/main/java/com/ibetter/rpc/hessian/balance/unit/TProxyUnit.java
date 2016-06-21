package com.ibetter.rpc.hessian.balance.unit;


public class TProxyUnit extends ProxyUnit {
	
	private volatile long timecost;

	private ProxyUnit proxyUnit;
	
	public TProxyUnit() {
	}

	public TProxyUnit(ProxyUnit proxyUnit) {
		
		/*BeanCopier beanCopier = BeanCopier.create(ProxyUnit.class, TProxyUnit.class, false);
		beanCopier.copy(proxyUnit, this, null);*/
		this.proxyUnit=proxyUnit;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TProxyUnit [").append(this.proxyUnit.toString()).append(",timecost=").append(timecost).append("]");
		return sb.toString();
	}

	public long getTimecost() {
		return timecost;
	}

	public void setTimecost(long timecost) {
		this.timecost = timecost;
	}

	@Override
	public Object getProxyObject() {
		return this.proxyUnit.getProxyObject();
	}

	@Override
	public int getWeight() {
		return this.proxyUnit.getWeight();
	}

	@Override
	public void setProxyObject(Object proxyObject) {
		this.proxyUnit.setProxyObject(proxyObject);
	}

	@Override
	public void setWeight(int weight) {
		this.proxyUnit.setWeight(weight);
	}

	@Override
	public int getStatus() {
		return this.proxyUnit.getStatus();
	}

	@Override
	public void setStatus(int status) {
		this.proxyUnit.setStatus(status);
	}

	@Override
	public long getTimestamp() {
		return this.proxyUnit.getTimestamp();
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.proxyUnit.setTimestamp(timestamp);
	}

	@Override
	public String getAccessPath() {
		return this.proxyUnit.getAccessPath();
	}

	@Override
	public void setAccessPath(String accessPath) {
		this.proxyUnit.setAccessPath(accessPath);
	}

}