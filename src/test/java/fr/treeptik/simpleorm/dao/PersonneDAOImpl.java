package fr.treeptik.simpleorm.dao;

import java.util.Date;
import java.util.List;

import fr.treeptik.simpleorm.model.Personne;
import fr.treeptik.simpleorm.orm.Condition;
import fr.treeptik.simpleorm.orm.Criteria;

public class PersonneDAOImpl implements PersonneDAO {

	private Criteria<Personne> criteria = new Criteria<Personne>(Personne.class);

	@Override
	public Personne save(Personne personne) {
		return criteria.save(personne);
	}
	
	@Override
	public List<Personne> findAll() {
		return criteria.list();
	}
	
	@Override
	public List<Personne> findByName(String name) {
		return criteria.add(Condition.eq("nom", name)).list();
	}
	
	@Override
	public List<Personne> findLikeName(String name) {
		return criteria.add(Condition.like("nom", name + "%")).list();
	}

	@Override
	public List<Personne> findByNameAndBirthdayBetween(String name,
			Date minDate, Date maxDate) {
		
		return criteria
				.add(Condition.eq("nom", name))
				.add(Condition.between("dateNaissance", minDate, maxDate))
				.list();
	}


}
