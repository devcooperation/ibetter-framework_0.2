 
package com.ibetter.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title:TODO</p>
 * @author zhaojun
 * @version	v1.0
 * <p>Date:2016年6月3日上午1:03:52</p>
 * <p>Description:TODO</p>
 */
public class MapConvertUtils2 {
	public static Map<String,Object> obj2map(Object obj,String ...prop){
		return obj2map(obj,true,prop);
	}
	public static Map<String,Object> obj2map(Object obj,boolean inclusive,String ...prop){
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			if (propertyDescriptors!=null && propertyDescriptors.length>0) {
				
				if (inclusive) {
					if (prop!=null && prop.length>0) {
						for (int j = 0; j < prop.length; j++) {
							for (int i = 0; i < propertyDescriptors.length; i++) {
								PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
								String propertyName=propertyDescriptor.getName();
								
								if (!"class".equalsIgnoreCase(propertyName)&&prop[j].equals(propertyName)) {
									
									Method readMethod = propertyDescriptor.getReadMethod();
									Object propertyVal = readMethod.invoke(obj);
									map.put(propertyName, propertyVal);
								}
							}
						}
					}else{
						for (int i = 0; i < propertyDescriptors.length; i++) {
							PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
							String propertyName=propertyDescriptor.getName();
							if (!"class".equalsIgnoreCase(propertyName)) {
								Method readMethod = propertyDescriptor.getReadMethod();
								Object propertyVal = readMethod.invoke(obj);
								map.put(propertyName, propertyVal);
							}
						}
					}
				}else{
					
					for (int i = 0; i < propertyDescriptors.length; i++) {
						PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
						String propertyName=propertyDescriptor.getName();
						if (!"class".equalsIgnoreCase(propertyName)) {
							boolean xinclude=false;
							for (int j = 0; j < prop.length; j++) {
								if (prop[j].equals(propertyName)) {
									xinclude=true;
								}
							}
							if (!xinclude) {
								Method readMethod = propertyDescriptor.getReadMethod();
								Object propertyVal = readMethod.invoke(obj);
								map.put(propertyName, propertyVal);
							}
						}
					}
					
				}
				
				
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return map;
	}
}
