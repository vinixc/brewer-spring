package br.com.vini.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.validation.SKU;

@Entity
@Table(name = "cerveja")
@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "cerveja_seq", name = "cerveja_seq")
public class Cerveja implements Serializable{
	private static final long serialVersionUID = -5620801313297311458L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cerveja_seq")
	private Long id;
	
	@SKU
	@NotBlank(message = "SKU é obrigatorio")
	private String sku;
	
	@NotBlank(message = "Nome é obrigatorio")
	private String nome;
	
	@Size(max = 50,message = "Descrição deve ter o tamanho entre 1 e 50")
	private String descricao;
	
	@NotNull(message = "Valor é obrigatorio")
	@DecimalMax(value = "9999999.99", message = "O valor da cerveja deve ser menor que R$9.999.999,99")
	@DecimalMin(value = "0.01", message = "O valor da cerveja deve ser maior que R$0.01")
	private BigDecimal valor;
	
	@DecimalMax(value = "100.0", message = "O Teor alcoolico deve ser igual ou menor que 100")
	@NotNull(message = "O teor alcoolico é obrigatorio.")
	@Column(name = "teor_alcoolico")
	private BigDecimal teorAlcoolico;
	
	@NotNull(message = "A comissão é obrigatoria")
	@DecimalMax(value = "100.0", message = "A comissão deve ser igual ou menor que 100")
	private BigDecimal comissao;
	
	@NotNull(message = "Estoque é obrigatorio ")
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@NotNull(message = "Origem é obrigatoria")
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@NotNull(message = "Sabor é obrigatorio")
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	
	@NotNull(message = "Estilo é obrigatorio")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estilo")
	private Estilo estilo;
	
	@Column(name = "foto")
	private String foto;
	
	@Column(name = "content_type")
	private String contentType;
	
	@PrePersist @PreUpdate
	private void prePersistUpdate() {
		this.sku = this.sku.toUpperCase();
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Long getId() {
		return id;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}
	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}
	public BigDecimal getComissao() {
		return comissao;
	}
	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	public Origem getOrigem() {
		return origem;
	}
	public void setOrigem(Origem origem) {
		this.origem = origem;
	}
	public Sabor getSabor() {
		return sabor;
	}
	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}
	public Estilo getEstilo() {
		return estilo;
	}
	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getFotoOrMock() {
		return !StringUtils.isEmpty(foto) ? foto : "cerveja-mock.png";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Cerveja [id=" + id + ", sku=" + sku + ", nome=" + nome + ", descricao=" + descricao + ", valor=" + valor
				+ ", teorAlcoolico=" + teorAlcoolico + ", comissao=" + comissao + ", quantidadeEstoque="
				+ quantidadeEstoque + ", origem=" + origem + ", sabor=" + sabor + ", estilo=" + estilo + "]";
	}
}
