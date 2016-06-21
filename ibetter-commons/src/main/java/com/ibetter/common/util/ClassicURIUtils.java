package com.ibetter.common.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class ClassicURIUtils {

	public static String toQStr(Map<String, String> paramMap) {
		
		StringBuilder qStrBuilder = new StringBuilder();
		Set<Entry<String, String>> entrySet = paramMap.entrySet();

		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			String val = entry.getValue();
			qStrBuilder.append(key).append("=").append(val).append("&");
		}
		String qstr = StringUtils.removeEnd(qStrBuilder.toString(), "&");
		return qstr;
	}
}
