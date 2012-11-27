package fr.treeptik.simpleorm.model;

public class Adresse {
	
	private Long id;
	private String rue;

	public Adresse() {

	}

	public Adresse(Long id, String rue) {
		super();
		this.id = id;
		this.rue = rue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

}
