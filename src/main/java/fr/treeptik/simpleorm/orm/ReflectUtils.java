package fr.treeptik.simpleorm.orm;


public class ReflectUtils {

	public static Object returnField(Object object, String fieldName) {

		fieldName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Object result = null;
		try {

			result = object.getClass().getMethod("get" + fieldName).invoke(object);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void setField(Object object, String fieldName, Object value) {

		fieldName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		
		try {
			object.getClass().getDeclaredMethod("set" + fieldName, value.getClass()).invoke(object, value);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
