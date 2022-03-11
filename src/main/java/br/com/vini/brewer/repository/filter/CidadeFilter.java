package br.com.vini.brewer.repository.filter;

import java.io.Serializable;

public class CidadeFilter implements Serializable, Filter{
	private static final long serialVersionUID = -949196651944213093L;
	
	private String nome;
	private String estado;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
