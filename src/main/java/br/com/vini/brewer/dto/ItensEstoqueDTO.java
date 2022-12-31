package br.com.vini.brewer.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ItensEstoqueDTO implements Serializable{ 
	private static final long serialVersionUID = -7414098603658921981L;
	
	private BigDecimal valorEstoque;
	private Long itensNoEstoque;
	
	public ItensEstoqueDTO(BigDecimal valorEstoque, Long itensNoEstoque) {
		this.valorEstoque = valorEstoque;
		this.itensNoEstoque = itensNoEstoque;
	}
	
	public ItensEstoqueDTO() {
	}

	public BigDecimal getValorEstoque() {
		return valorEstoque;
	}
	public void setValorEstoque(BigDecimal valorEstoque) {
		this.valorEstoque = valorEstoque;
	}
	public Long getItensNoEstoque() {
		return itensNoEstoque;
	}
	public void setItensNoEstoque(Long itensNoEstoque) {
		this.itensNoEstoque = itensNoEstoque;
	}

	@Override
	public String toString() {
		return "ItensEstoqueDTO [valorEstoque=" + valorEstoque + ", itensNoEstoque=" + itensNoEstoque + "]";
	}

}
