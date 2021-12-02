package br.com.vini.brewer.model;

import org.hibernate.validator.constraints.NotBlank;

public class Cidade {
	
	@NotBlank
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
