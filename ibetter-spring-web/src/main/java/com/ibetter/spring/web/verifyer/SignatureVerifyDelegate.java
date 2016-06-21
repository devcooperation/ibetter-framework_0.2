package com.ibetter.spring.web.verifyer;

import java.util.TreeMap;

import com.ibetter.spring.web.exception.GrantException;
import com.ibetter.spring.web.servlet.RequestDelegate;
import com.ibetter.spring.web.token.TokenRepository;

public interface SignatureVerifyDelegate {
	public boolean doVerify(TreeMap<String, Object> verifyDataMap, String token, String signature)throws GrantException;
	public boolean doVerify(RequestDelegate reqDelegate,String[] properties,long time,TokenRepository tokenRepository)throws GrantException;
}
