package fr.treeptik.simpleorm.orm;

public class Condition {

	private String propertyName;
	private String columnName;
	private Object[] values;
	private String sql;

	public static Condition eq(String propertyName, Object value) {
		return new Condition(" = ? ", propertyName, value);
	}

	public static Condition lowerThan(String propertyName, Object value) {
		return new Condition(" < ? ", propertyName, value);
	}

	public static Condition greaterThan(String propertyName, Object value) {
		return new Condition(propertyName + " > ? ", propertyName, value);
	}

	public static Condition like(String propertyName, Object value) {
		return new Condition(" like ? ", propertyName, value);
	}
	
	public static Condition between(String propertyName, Object minValue, Object maxValue ) {
		return new Condition(" between ? And ? ", propertyName, minValue, maxValue);
	}
	
	public Condition(String sql, String propertyName, Object... values) {
		super();
		this.setPropertyName(propertyName);
		this.values = values;
		this.sql = sql;
	}

	
	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public String getSql() {
		return  columnName + " " +sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	
	
	
}
