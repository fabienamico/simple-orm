package fr.treeptik.simpleorm.model;

public class Moteur {
	private Long id;
	private Integer puissance;

	public Moteur() {

	}

	public Moteur(Long id, Integer puissance) {
		super();
		this.id = id;
		this.puissance = puissance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPuissance() {
		return puissance;
	}

	public void setPuissance(Integer puissance) {
		this.puissance = puissance;
	}

}
