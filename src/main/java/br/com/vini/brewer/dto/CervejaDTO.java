package br.com.vini.brewer.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.vini.brewer.model.Origem;

public class CervejaDTO implements Serializable{
	private static final long serialVersionUID = -2232919174604134584L;

	private Long id;
	private String sku;
	private String nome;
	private String origem;
	private BigDecimal valor;
	private String foto;
	
	public CervejaDTO(Long id, String sku, String nome, Origem origem, BigDecimal valor, String foto) {
		this.id = id;
		this.sku = sku;
		this.nome = nome;
		this.origem = origem.getDescricao();
		this.valor = valor;
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
}
