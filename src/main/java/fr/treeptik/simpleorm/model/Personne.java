package fr.treeptik.simpleorm.model;

import java.util.Date;

public class Personne {

	private Long id;
	private String nom;
	private String prenom;
	private Date dateNaissance;

	private Adresse adresse;
	private Voiture voiture;
	
	public Personne() {

	}

	public Personne(Long id, String nom, String prenom, Date dateNaissance) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setVoiture(Voiture voiture) {
		this.voiture = voiture;
	}

	public Voiture getVoiture() {
		return voiture;
	}

}
