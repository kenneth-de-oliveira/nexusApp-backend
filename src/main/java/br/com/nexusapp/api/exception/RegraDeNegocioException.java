package br.com.nexusapp.api.exception;

public class RegraDeNegocioException extends RuntimeException {

	private static final long serialVersionUID = -3224049423066380063L;

	public RegraDeNegocioException(String msg) {
        super(msg);
    }

    public RegraDeNegocioException(String msg, Throwable cause) {
        super(msg, cause);
    }
	
}
