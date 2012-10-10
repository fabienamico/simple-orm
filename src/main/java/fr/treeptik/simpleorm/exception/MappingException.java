package fr.treeptik.simpleorm.exception;

public class MappingException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MappingException(String message){
		super (message);
	}

}
