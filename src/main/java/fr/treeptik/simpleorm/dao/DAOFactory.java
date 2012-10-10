package fr.treeptik.simpleorm.dao;

public class DAOFactory {

	public static PersonneDAO getPersonneDAO(){
		
		return new PersonneDAOImpl();
		
	}
	
}
