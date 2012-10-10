package fr.treeptik.simpleorm.orm;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;

import fr.treeptik.simpleorm.exception.DBException;
import fr.treeptik.simpleorm.exception.MappingException;
import fr.treeptik.simpleorm.xmlbinding.ClassMapping;
import fr.treeptik.simpleorm.xmlbinding.Property;
import fr.treeptik.simpleorm.xmlbinding.SimpleMapping;

public class MappingManager {

	private static  Map<Class<?>, ClassMapping> tableInfoMap = null;


	public static Map<Class<?>, ClassMapping> getTableInfoMap(){
		
		if(tableInfoMap==null){
			SimpleMapping mapping = readMapping(new File("sample-mapping.xml"));
			veriffMapping(mapping);
			initTableInfoMap(mapping);
		}
		
		return tableInfoMap;
		
	}
	
	private static SimpleMapping readMapping(File mappingfile) {

		SimpleMapping simpleMapping = null;

		try {
			JAXBContext context = JAXBContext
					.newInstance("fr.treeptik.simpleorm.xmlbinding");

			simpleMapping = (SimpleMapping) context.createUnmarshaller().unmarshal(mappingfile);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return simpleMapping;

	}

	private static void veriffMapping(SimpleMapping mapping)
			throws MappingException {

		try {

			Connection conn = DatabaseManager.getConnection();
			for (ClassMapping classMapping : mapping.getClassMapping()) {

				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery("Select * from "
						+ classMapping.getTableName() + " Where 1=2");

				ResultSetMetaData rsmd = rs.getMetaData();
				for (Property property : classMapping.getProperty()) {

					String columnName = property.getColumn();
					if (!checkIfColumnExist(columnName, rsmd)) {
						throw new MappingException("Error column " + columnName
								+ " does not exist");
					}

				}

			}

		} catch (DBException e) {
			e.printStackTrace();
			throw new MappingException(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MappingException(e.getMessage());
		}

	}

	private static void initTableInfoMap(SimpleMapping mapping)
			throws MappingException {

		tableInfoMap = new HashMap<Class<?>, ClassMapping>();
		
		try {

			for (ClassMapping classMapping : mapping.getClassMapping()) {

				tableInfoMap.put(Class.forName(classMapping.getClassName()),
						classMapping);

			}
		} catch (ClassNotFoundException e) {
			throw new MappingException(e.getMessage());
		}
	}

	private static Boolean checkIfColumnExist(String columnName,
			ResultSetMetaData meta) throws MappingException {

		int numCol;
		try {
			numCol = meta.getColumnCount();
			for (int i = 1; i < numCol + 1; i++) {
				if (meta.getColumnName(i).equals(columnName)) {
					return true;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MappingException(e.getMessage());
		}

		return false;

	}

}
