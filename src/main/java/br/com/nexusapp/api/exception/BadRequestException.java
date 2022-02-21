package br.com.nexusapp.api.exception;

public class BadRequestException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = -860366253898178508L;

	public BadRequestException(String msg){
        super(msg);
    }

    public BadRequestException(String msg, Throwable cause){
        super(msg, cause);
    }
}
