package fr.treeptik.simpleorm.orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.treeptik.simpleorm.exception.DBException;
import fr.treeptik.simpleorm.xmlbinding.ClassMapping;
import fr.treeptik.simpleorm.xmlbinding.Property;

public class Criteria <T> {

	private List<Condition> conditions = new LinkedList<Condition>();
	private ClassMapping mapping;
	private Map<String, Property> mappingPropertyAsKey = new HashMap<String, Property>();
	
	
	public Criteria(Class<T> type){
		mapping = MappingManager.getTableInfoMap().get(type);
		
		for(Property property : mapping.getProperty()){
			mappingPropertyAsKey.put(property.getName(), property);
		}
		
	}
	
	public Criteria<T> add(Condition condition){
		condition.setColumnName(mappingPropertyAsKey.get(condition.getPropertyName()).getColumn());
		this.conditions.add(condition);
		return this;
	}
	
	public List<T> list(){
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("Select * From ").append(mapping.getTableName());
		
		if(conditions.size()>0){
			queryBuilder.append(" Where ");
			
			for (Condition condition : conditions){
				queryBuilder.append(condition.getSql()).append(" AND ");
			}
			
			int lastAnd = queryBuilder.lastIndexOf("AND");
			queryBuilder.delete(lastAnd, lastAnd+3);
		}
		
		String sql =  queryBuilder.toString();
		System.out.println(sql);
		
		ResultSet rs = executeQuery(sql, conditions);
		return getListEntite(rs);
		
		
	}
	
	public T save(T entite){
		try {

			Connection connection = DatabaseManager.getConnection();

			// Query builder
			StringBuilder query = new StringBuilder("Insert INTO ");
			query.append(mapping.getTableName()).append(" (");
			for (Property property : mapping.getProperty()) {
				query.append(" ").append(property.getColumn()).append(",");
			}
			// Remove last ','
			query.deleteCharAt(query.lastIndexOf(","));
			// Add Values
			query.append(") VALUES (");
			for (int i = 0; i < mapping.getProperty().size(); i++) {
				query.append("?");
				if (i < mapping.getProperty().size() - 1) {
					query.append(",");
				}
			}
			query.append(")");

			PreparedStatement statement = 
				connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);

			// Add Parameter
			for (int i = 0; i < mapping.getProperty().size(); i++) {
				Property property = mapping.getProperty().get(i);
				statement.setObject(i + 1,
						ReflectUtils.returnField(entite, property.getName()));
			}

			// Execute Query
			statement.executeUpdate();

			// Get Generated Key
			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			Object key = keys.getObject(1);
			ReflectUtils.setField(entite, mapping.getId().getName(), key);
			
			
		} catch (DBException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entite;
	}
	
	
	@SuppressWarnings("unchecked")
	private T getEntite(ResultSet resultSet){
		T entite =null;
		try {
			entite = (T) Class.forName(mapping.getClassName()).newInstance();
			for (Property property : mapping.getProperty()){
				ReflectUtils.setField(entite, property.getName(), getParam(resultSet.getObject(property.getColumn())));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return entite;
	}
	
	private List<T> getListEntite(ResultSet resultSet){
		
		List<T> result = new LinkedList<T>();
		try {
			while(resultSet.next()){
				result.add(getEntite(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ResultSet executeQuery(String sql, List<Condition> params){
		
		PreparedStatement statement = null;
		
		try {
			
			Connection connection = DatabaseManager.getConnection();
			statement = connection.prepareStatement(sql);
			if(params!=null){
				for (int i = 0; i < params.size(); i++){
					Condition condition = params.get(i);
					// Plusieur param ex : Between
					if(condition.getValues().length==2){
						statement.setObject(i+1, getParam(condition.getValues()[0]));
						statement.setObject(i+2, getParam(condition.getValues()[1]));
						i+=1;
					}else{
						
						statement.setObject(i+1, getParam(condition.getValues()[0]));
						
					}
					
				}
			}
			
			return statement.executeQuery();
			
		} catch (DBException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
		
	}
	
	private Object getParam(Object object){
		Object result = object ;
		if (object instanceof java.util.Date){
			java.util.Date utilDate = (java.util.Date) object;
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			result = sqlDate;
		}
		
		if (object instanceof java.sql.Date){
			java.sql.Date sqlDate = (java.sql.Date) object;
			java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
			result = utilDate;
		}
		
		return result;
		
	}
	
}
