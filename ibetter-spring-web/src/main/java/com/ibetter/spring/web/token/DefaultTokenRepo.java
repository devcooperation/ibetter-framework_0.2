package com.ibetter.spring.web.token;


import com.ibetter.spring.web.servlet.RequestDelegate;

/**
 * 默认token仓库实现
 * @author zhaojun
 * 2016年2月2日 下午1:42:10
 */
public class DefaultTokenRepo extends TokenRepositoryAdapter {
	@Override
	public String handle(RequestDelegate request) {
		return TokenRepoUtil.getToken();
	}

	@Override
	public TokenBind retriveTokenBind(String token) {
		return null;
	}
}
