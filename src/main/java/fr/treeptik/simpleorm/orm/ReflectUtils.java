package fr.treeptik.simpleorm.orm;

import org.apache.commons.beanutils.PropertyUtils;


public class ReflectUtils {

	public static Object returnField(Object object, String fieldName) {

// Utilisé sans BeanUtils
//		fieldName = fieldName.substring(0, 1).toUpperCase()
//				+ fieldName.substring(1);
		Object result = null;
		try {
			
			//result = object.getClass().getMethod("get" + fieldName).invoke(object);
			result = PropertyUtils.getProperty(object, fieldName);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void setField(Object object, String fieldName, Object value) {

// Utilisé sans BeanUtils
//		fieldName = fieldName.substring(0, 1).toUpperCase()
//				+ fieldName.substring(1);
		
		try {
			
			//object.getClass().getDeclaredMethod("set" + fieldName, value.getClass()).invoke(object, value);
			PropertyUtils.setProperty(object, fieldName, value);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
