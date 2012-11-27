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
import fr.treeptik.simpleorm.xmlbinding.OneToMany;
import fr.treeptik.simpleorm.xmlbinding.Property;

public class Criteria<T> {

	private List<Condition> conditions = new LinkedList<Condition>();
	private ClassMapping mapping;
	private Map<String, Property> mappingPropertyAsKey = new HashMap<String, Property>();

	public Criteria(Class<T> type) {
		mapping = MappingManager.getTableInfoMap().get(type);

		for (Property property : mapping.getProperty()) {
			mappingPropertyAsKey.put(property.getName(), property);
		}

	}

	public Criteria<T> add(Condition condition) {
		condition.setColumnName(mappingPropertyAsKey.get(
				condition.getPropertyName()).getColumn());
		this.conditions.add(condition);
		return this;
	}

	public List<T> list() {

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("Select * From ").append(mapping.getTableName());

		if (conditions.size() > 0) {
			queryBuilder.append(" Where ");

			for (Condition condition : conditions) {
				queryBuilder.append(condition.getSql()).append(" AND ");
			}

			int lastAnd = queryBuilder.lastIndexOf("AND");
			queryBuilder.delete(lastAnd, lastAnd + 3);
		}

		String sql = queryBuilder.toString();
		System.out.println(sql);

		ResultSet rs = executeQuery(sql, conditions);
		return getListEntite(rs);

	}

	public T save(T entite) {
		try {

			// Save OneToMany Object
			if (mapping.getOneToMany().size() > 0) {
				saveOneToMany(entite);
			}

			Connection connection = DatabaseManager.getConnection();

			// Query builder
			StringBuilder query = new StringBuilder("Insert INTO ");
			query.append(mapping.getTableName()).append(" (");
			
			// Query for property
			for (Property property : mapping.getProperty()) {
				query.append(" ").append(property.getColumn()).append(",");
			}
			
			//Query for One-to-many
			for(OneToMany oneToMany : mapping.getOneToMany()){
				query.append(" ").append(oneToMany.getColumn()).append(",");
			}
			
			
			// Remove last ','
			query.deleteCharAt(query.lastIndexOf(","));
			// Add Values
			
			Integer nbValues = mapping.getProperty().size() + mapping.getOneToMany().size();
			query.append(") VALUES (");
			for (int i = 0; i < nbValues; i++) {
				query.append("?");
				if (i < nbValues - 1) {
					query.append(",");
				}
			}
			query.append(")");

			System.out.println(query.toString());
			
			PreparedStatement statement = connection.prepareStatement(
					query.toString(), Statement.RETURN_GENERATED_KEYS);

			// Add Parameter Property
			int indexValue = 1; // Index du ? dans la requte
			for (int i = 0; i < mapping.getProperty().size(); i++) {
				Property property = mapping.getProperty().get(i);
				statement.setObject(indexValue, ReflectUtils.returnField(entite, property.getName()));
				indexValue++;
			}

			// Add Parameter OneTomany
			for (int i = 0; i < mapping.getOneToMany().size(); i++) {
				OneToMany oneToMany = mapping.getOneToMany().get(i);
				Object dependObject = ReflectUtils.returnField(entite, oneToMany.getName());
				Object dependId = ReflectUtils.returnField(dependObject, oneToMany.getId().getName());
				statement.setObject(indexValue,dependId);
				indexValue++;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveOneToMany(T entite) {

		for (OneToMany oneToMany : mapping.getOneToMany()) {
			try {
				Object dependClass = ReflectUtils.returnField(entite,oneToMany.getName());
				if (dependClass!=null){
					Class<?> loadClass = Class.forName(oneToMany.getClazz());
					Criteria criteria = new Criteria(loadClass);
					criteria.save(dependClass);
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 

		}

	}

	@SuppressWarnings("unchecked")
	private T getEntite(ResultSet resultSet) {
		T entite = null;
		try {
			entite = (T) Class.forName(mapping.getClassName()).newInstance();
			for (Property property : mapping.getProperty()) {
				ReflectUtils.setField(entite, property.getName(),
						getParam(resultSet.getObject(property.getColumn())));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return entite;
	}

	private List<T> getListEntite(ResultSet resultSet) {

		List<T> result = new LinkedList<T>();
		try {
			while (resultSet.next()) {
				result.add(getEntite(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	private ResultSet executeQuery(String sql, List<Condition> params) {

		PreparedStatement statement = null;

		try {

			Connection connection = DatabaseManager.getConnection();
			statement = connection.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					Condition condition = params.get(i);
					// Plusieur param ex : Between
					if (condition.getValues().length == 2) {
						statement.setObject(i + 1,
								getParam(condition.getValues()[0]));
						statement.setObject(i + 2,
								getParam(condition.getValues()[1]));
						i += 1;
					} else {

						statement.setObject(i + 1,
								getParam(condition.getValues()[0]));

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

	private Object getParam(Object object) {
		Object result = object;
		if (object instanceof java.util.Date) {
			java.util.Date utilDate = (java.util.Date) object;
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			result = sqlDate;
		}

		if (object instanceof java.sql.Date) {
			java.sql.Date sqlDate = (java.sql.Date) object;
			java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
			result = utilDate;
		}

		return result;

	}

}
