package br.com.nexusapp.api.exception;

public class ConflictException extends RuntimeException{
 
	private static final long serialVersionUID = 3892040084248344160L;

	public ConflictException(String msg){
        super(msg);
    }

    public ConflictException(String msg, Throwable cause){
        super(msg, cause);
    }
}
