package br.com.vini.brewer.repository.filter;

import java.io.Serializable;
import java.util.List;

import br.com.vini.brewer.model.Grupo;

public class UsuarioFilter implements Serializable,Filter{
	private static final long serialVersionUID = -7864254933115494504L;
	
	private String nome;
	private String email;
	private Boolean ativo;
	private List<Grupo> grupos;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
}
