package com.ibetter.spring.data.query.param;

import com.ibetter.spring.data.query.result.Pagination;

 

/**
 * 分页查询参数
 * @author zhaojun
 * 2015年10月29日 下午2:01:01
 */
public class PaginParam extends QParam{
	
	
	public PaginParam(long offset,int limit,QParam qParam ) {
		this(offset,limit);
		this.addQParam(qParam);
	}


	public QParam addQParam(QParam qParam){
		this.param.putAll(qParam.getQParam());
		return this;
	}


	/**
	 * @param offset	其实记录条数
	 * @param limit		页面大小
	 */
	public PaginParam(long offset,int limit ) {
		param.put(Pagination._offset, offset-1<0?0:offset-1);
		param.put(Pagination._limit,limit);
	}

	

}
