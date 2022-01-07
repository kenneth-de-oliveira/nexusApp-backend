package br.com.nexusapp.api.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7003622248519691101L;

	public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

