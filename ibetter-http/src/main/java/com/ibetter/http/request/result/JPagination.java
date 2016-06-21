package com.ibetter.http.request.result;


/**
 * 分页返回模型
 * @author zhaojun
 * 2015年10月27日 上午10:18:41
 */
public class JPagination<T> {
	
	
	private long offset; 
	private int limit;  
	private long total;
	private T data;
	
	
	public JPagination() {
		super();
	}
	
	
 


	/**
	 * 起始记录数
	 * @return int
	 */
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	/**
	 * 页面大小
	 * @return int
	 */
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	/**
	 * 总条数
	 * @return int
	 */
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}
	
	/**
	 * 分页数据
	 * @return
	 */
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public static final String _offset="offset";
	public static final String _limit="limit";
}
