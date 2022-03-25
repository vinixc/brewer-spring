package br.com.vini.brewer.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.StatusVenda;

public class VendaFilter implements Filter{
	
	private Long id;
	private StatusVenda status;
	
	private LocalDate desde;
	private LocalDate ate;
	
	private BigDecimal valorMinimo;
	private BigDecimal valorMaximo;
	
	private String nomeCliente;
	private String cpfOuCnpjCliente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StatusVenda getStatus() {
		return status;
	}
	public void setStatus(StatusVenda status) {
		this.status = status;
	}
	public LocalDate getDesde() {
		return desde;
	}
	public void setDesde(LocalDate desde) {
		this.desde = desde;
	}
	public LocalDate getAte() {
		return ate;
	}
	public void setAte(LocalDate ate) {
		this.ate = ate;
	}
	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getCpfOuCnpjCliente() {
		return cpfOuCnpjCliente;
	}
	public void setCpfOuCnpjCliente(String cpfOuCnpjCliente) {
		this.cpfOuCnpjCliente = cpfOuCnpjCliente;
	}
	
	public boolean hasSearchForClient() {
		return hasNomeCliente() || hasCpfOuCnpjCliente();
	}
	
	public boolean hasNomeCliente() {
		return StringUtils.hasText(this.nomeCliente);
	}
	
	public boolean hasCpfOuCnpjCliente() {
		return StringUtils.hasText(this.cpfOuCnpjCliente);
	}

}
