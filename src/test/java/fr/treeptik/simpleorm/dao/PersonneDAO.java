package fr.treeptik.simpleorm.dao;

import java.util.Date;
import java.util.List;

import fr.treeptik.simpleorm.model.Personne;

public interface PersonneDAO {

	Personne save(Personne personne);

	List<Personne> findByName(String name);

	List<Personne> findLikeName(String name);

	List<Personne> findByNameAndBirthdayBetween(String name, Date minDate,
			Date maxDate);
	
	List<Personne> findAll();

}