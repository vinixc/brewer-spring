package br.com.vini.brewer.model;

public enum StatusVenda {

	ORCAMENTO("Orçamento"),
	EMITIDA("Emitida"),
	CANCELADA("Cancelada");
	
	private String descricao;
	
	StatusVenda(String msg) {
		descricao = msg;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
