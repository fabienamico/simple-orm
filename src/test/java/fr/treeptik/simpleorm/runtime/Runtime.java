package fr.treeptik.simpleorm.runtime;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.treeptik.simpleorm.dao.DAOFactory;
import fr.treeptik.simpleorm.dao.PersonneDAO;
import fr.treeptik.simpleorm.model.Adresse;
import fr.treeptik.simpleorm.model.Moteur;
import fr.treeptik.simpleorm.model.Personne;
import fr.treeptik.simpleorm.model.Voiture;
import fr.treeptik.simpleorm.orm.Condition;
import fr.treeptik.simpleorm.orm.Criteria;

public class Runtime {

	public static void main(String[] args) throws Exception {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date naissanceTorval = dateFormat.parse("06/03/1970");		
		
		PersonneDAO personneDAO = DAOFactory.getPersonneDAO();
		
		Personne torval = new Personne(null, "Torval", "Linus", naissanceTorval);
		Adresse adresse = new Adresse(null, "Rue de la paix ");
		torval.setAdresse(adresse);
		
		Moteur moteur = new Moteur(null, 100);  
		Voiture voiture = new Voiture(null, "AUDI", moteur);
		
		torval.setVoiture(voiture);
		
		personneDAO.save(torval);
		
		Criteria<Adresse> criteria = new Criteria<Adresse>(Adresse.class);
		criteria.save(adresse);
		
		
		
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
		
//		System.out.println("###### find ALL");
//		List<Personne> personnes = personneDAO.findAll();
//		System.out.println("Size = " + personnes.size());
//		System.out.println("Nom = " + personnes.get(0).getNom());
//		
//		System.out.println("###### findByName ");
//		personnes = personneDAO.findByName("Torval");
//		System.out.println("Size = " + personnes.size());
//		System.out.println("Nom = " + personnes.get(0).getNom());
//		
//		System.out.println("###### findLikeName ");
//		personnes = personneDAO.findLikeName("To");
//		System.out.println("Size = " + personnes.size());
//		System.out.println("Nom = " + personnes.get(0).getNom());
//		
//		System.out.println("###### findLikeName ");
//		Date minDate = dateFormat.parse("01/01/1970");
//		Date maxDate = dateFormat.parse("30/12/1980");
//		
//		personnes = personneDAO.findByNameAndBirthdayBetween("Torval", minDate, maxDate);
//		System.out.println("Size = " + personnes.size());
//		System.out.println("Nom = " + personnes.get(0).getNom());
		
		
	}
	
}
