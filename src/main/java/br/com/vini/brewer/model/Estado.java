package br.com.vini.brewer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "estado")
@SequenceGenerator(name = "estado_seq", sequenceName = "estado_seq", allocationSize = 1, initialValue = 1)
public class Estado implements Serializable{
	private static final long serialVersionUID = 1618359222671758231L;
	
	@Id
	@GeneratedValue(generator = "estado_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Size(min = 3,max = 50, message = "Nome deve ter entre 3 e 50 caracteres.")
	@NotBlank(message = "nome é obrigatorio")
	private String nome;
	
	@Size(min = 2,max = 2, message = "Sigla deve ter 2 caracteres.")
	@NotBlank(message = "sigla é obrigatorio")
	private String sigla;

	public Estado() {}

	public Estado(String nome, String sigla) {
		this.nome = nome;
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Long getId() {
		return id;
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
		Estado other = (Estado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Estado [id=" + id + ", nome=" + nome + ", sigla=" + sigla + "]";
	}
}
