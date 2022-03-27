package br.com.vini.brewer.exception;

public class ImpossivelExcluirEntidadeException extends RuntimeException {
	private static final long serialVersionUID = 4766708710022258851L;
	
	public ImpossivelExcluirEntidadeException(String msg) {
		super(msg);
	}

}
