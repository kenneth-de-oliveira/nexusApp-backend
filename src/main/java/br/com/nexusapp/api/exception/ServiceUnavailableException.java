package br.com.nexusapp.api.exception;

public class ServiceUnavailableException extends RuntimeException{

	public ServiceUnavailableException(String msg){
        super(msg);
    }

    public ServiceUnavailableException(String msg, Throwable cause){
        super(msg, cause);
    }
}