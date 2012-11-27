package fr.treeptik.simpleorm.model;

public class Voiture {
	
	private Long id;
	private String modele;
	private Moteur moteur;
	
	public Voiture(){
		
	}
	
	public Voiture(Long id, String modele, Moteur moteur) {
		super();
		this.id = id;
		this.modele = modele;
		this.moteur = moteur;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public Moteur getMoteur() {
		return moteur;
	}
	public void setMoteur(Moteur moteur) {
		this.moteur = moteur;
	}
	
	
	
	
}
