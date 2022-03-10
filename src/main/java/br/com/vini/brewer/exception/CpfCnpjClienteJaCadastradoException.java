package br.com.vini.brewer.exception;

public class CpfCnpjClienteJaCadastradoException extends RuntimeException {
	private static final long serialVersionUID = -9118991862908731833L;
	
	public CpfCnpjClienteJaCadastradoException(String message){
		super(message);
	}
}
