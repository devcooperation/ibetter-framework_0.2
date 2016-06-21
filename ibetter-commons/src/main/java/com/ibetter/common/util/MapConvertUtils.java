package com.ibetter.common.util;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * 对象转换成Map
 * @author zhaojun
 * 2015年10月29日 下午3:04:08
 */
public class MapConvertUtils {
	
	protected static String _get_prefix="get";
	public static Map<String,Object> object2Map(Object obj,String ...prop){
		return object2Map(obj, true, prop);
	}
	
	
	/**
	 * 将一个java bean的部分属性转换到一个Map对象中
	 * 
	 * @param obj
	 * @param inclusive		转换结果：true 表示只包含 prop中的属性,false 表示取反 排除prop中所有的属性
	 * @param prop			操作的属性名称
	 * @return
	 */
	public static Map<String,Object> object2Map(Object obj,boolean inclusive,String ...prop){
		
			Class<? extends Object> clazz = obj.getClass();
			Method[] methods = clazz.getMethods();
			Field[] fields = clazz.getDeclaredFields();
			Map<String,Object> objMap=new HashMap<String, Object>();
			for (Method method : methods) {
				String methodName=method.getName();
				if(StringUtils.startsWith(methodName, _get_prefix)){
					String attrUpper=StringUtils.remove(methodName, _get_prefix);
					char attrHeader = attrUpper.charAt(0);
					String tail=StringUtils.removeStart(attrUpper, String.valueOf(attrHeader));
					String header=StringUtils.lowerCase(CharUtils.toString(attrHeader));
					String fName=StringUtils.join(header,tail);
					for (Field field : fields) {
						String fieldName=field.getName();
						
						if(ArrayUtils.isNotEmpty(prop)){
						
							if(!inclusive){
								if(!ArrayUtils.contains(prop, fieldName)){
									if(StringUtils.equals(fName, fieldName)){
										invokeProperty(obj, objMap, method, fName);
									}
								}
							}else{
								if(ArrayUtils.contains(prop, fieldName)){
									if(StringUtils.equals(fName, fieldName)){
										invokeProperty(obj, objMap, method, fName);
									}
								}
							}
						}else{
							if(StringUtils.equals(fName, fieldName)){
								invokeProperty(obj, objMap, method, fName);
							}
						}
						
					}
					
				}
			}
			return objMap;
	}
	private static void invokeProperty(Object obj, Map<String, Object> objMap, Method method, String fName) {
		try {
			Object result=method.invoke(obj);
			objMap.put(fName, result);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	 
}


 