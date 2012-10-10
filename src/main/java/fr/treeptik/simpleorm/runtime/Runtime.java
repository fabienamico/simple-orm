package fr.treeptik.simpleorm.runtime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.treeptik.simpleorm.dao.DAOFactory;
import fr.treeptik.simpleorm.dao.PersonneDAO;
import fr.treeptik.simpleorm.model.Personne;

public class Runtime {

	public static void main(String[] args) throws Exception {
		
		PersonneDAO personneDAO = DAOFactory.getPersonneDAO();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date naissanceTorval = dateFormat.parse("06/03/1970");
		Date naissanceGosling = dateFormat.parse("06/03/1980");
		Date naissanceKing = dateFormat.parse("06/03/1990");
		Date naissanceTooo = dateFormat.parse("06/03/1970");
		
//		INIT DATA
		
//		Personne torval = new Personne(null, "Torval", "Linus", naissanceTorval);
//		torval = personneDAO.save(torval);
//		System.out.println("Save Personne id = " + torval.getId());
//		
//		Personne torval2 = new Personne(null, "Torval", "Linus", naissanceGosling);
//		personneDAO.save(torval2);
//		System.out.println("Save Personne id = " + torval2.getId());
//		
//		Personne gosling = new Personne(null, "Gosling", "James", naissanceGosling);
//		personneDAO.save(gosling);
//		System.out.println("Save Personne id = " + gosling.getId());
//	
//		Personne king = new Personne(null, "King", "gavin", naissanceKing);
//		personneDAO.save(king);	
//		System.out.println("Save Personne id = " + king.getId());
//		
//		Personne tooo= new Personne(null, "Tooo", "Liii", naissanceTooo);
//		personneDAO.save(tooo);
//		System.out.println("Save Personne id = " + tooo.getId());
		
		System.out.println("###### find ALL");
		List<Personne> personnes = personneDAO.findAll();
		System.out.println("Size = " + personnes.size());
		System.out.println("Nom = " + personnes.get(0).getNom());
		
		System.out.println("###### findByName ");
		personnes = personneDAO.findByName("Torval");
		System.out.println("Size = " + personnes.size());
		System.out.println("Nom = " + personnes.get(0).getNom());
		
		System.out.println("###### findLikeName ");
		personnes = personneDAO.findLikeName("To");
		System.out.println("Size = " + personnes.size());
		System.out.println("Nom = " + personnes.get(0).getNom());
		
		System.out.println("###### findLikeName ");
		Date minDate = dateFormat.parse("01/01/1970");
		Date maxDate = dateFormat.parse("30/12/1980");
		
		personnes = personneDAO.findByNameAndBirthdayBetween("Torval", minDate, maxDate);
		System.out.println("Size = " + personnes.size());
		System.out.println("Nom = " + personnes.get(0).getNom());
		
		
	}
	
}
