package br.com.vini.brewer.dto;

import java.io.Serializable;

public class VendaOrigem implements Serializable{
	private static final long serialVersionUID = 2760311833754884241L;
	
	private String mes;
	private Integer totalNacional;
	private Integer totalInternacional;
	
	public VendaOrigem(String mes, Integer totalNacional, Integer totalInternacional) {
		this.mes = mes;
		this.totalNacional = totalNacional;
		this.totalInternacional = totalInternacional;
	}
	
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public Integer getTotalNacional() {
		return totalNacional;
	}
	public void setTotalNacional(Integer totalNacional) {
		this.totalNacional = totalNacional;
	}
	public Integer getTotalInternacional() {
		return totalInternacional;
	}
	public void setTotalInternacional(Integer totalInternacional) {
		this.totalInternacional = totalInternacional;
	}

	@Override
	public String toString() {
		return "VendaOrigem [mes=" + mes + ", totalNacional=" + totalNacional + ", totalInternacional="
				+ totalInternacional + "]";
	}

}
