package br.com.nexusapp.api.exception;

public class ServiceUnavailableException extends RuntimeException{

	private static final long serialVersionUID = 5734544386026469718L;

    public ServiceUnavailableException(){}

	public ServiceUnavailableException(String msg){
        super(msg);
    }

    public ServiceUnavailableException(String msg, Throwable cause){
        super(msg, cause);
    }
}