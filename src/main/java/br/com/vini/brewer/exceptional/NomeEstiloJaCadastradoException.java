package br.com.vini.brewer.exceptional;

public class NomeEstiloJaCadastradoException extends RuntimeException {
	private static final long serialVersionUID = -4786420105861114799L;
	
	public NomeEstiloJaCadastradoException(String msg) {
		super(msg);
	}

}
