package com.ibetter.spring.web.token;


import java.util.TreeMap;

import com.ibetter.spring.web.exception.GrantException;
import com.ibetter.spring.web.verifyer.SignatureVerifyAdpter;
import com.ibetter.spring.web.verifyer.SignatureVerifyDelegate;
import com.ibetter.spring.web.verifyer.SignatureVerifyer;


public class DefaultSignatureVerify extends SignatureVerifyAdpter implements SignatureVerifyDelegate {

	@Override
	public boolean doVerify(TreeMap<String, Object> verifyDataMap, String token, String signature) throws GrantException {
		SignatureVerifyer verifyer = SignatureVerifyer.create(verifyDataMap,token,signature);
		if(!verifyer.execute()){
			throw new GrantException("Verification is failing!");
		}
		return true;
	}
	 
}
