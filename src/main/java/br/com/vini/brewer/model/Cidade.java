package br.com.vini.brewer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cidade")
@SequenceGenerator(allocationSize = 1,initialValue = 1, name = "cidade_seq", sequenceName = "cidade_seq")
public class Cidade implements Serializable{
	private static final long serialVersionUID = -8354010227056564710L;
	
	@Id
	@GeneratedValue(generator = "cidade_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatorio")
	@Size(min = 3,max = 50, message = "Nome deve ter entre 3 e 50 caracteres.")
	private String nome;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estado_id")
	@JsonIgnore
	private Estado estado;

	public Cidade() { }

	public Cidade(String nome, Estado estado) {
		this.nome = nome;
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cidade [id=" + id + ", nome=" + nome + ", estado=" + estado + "]";
	}

}
