package br.com.vini.brewer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table; 

@Entity
@Table(name = "permissao")
@SequenceGenerator(allocationSize = 1, initialValue = 1,  name = "permissao_seq", sequenceName = "permissao_seq")
public class Permissao implements Serializable{
	private static final long serialVersionUID = 8308344127684613478L;
	
	@Id
	@GeneratedValue(generator = "permissao_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		Permissao other = (Permissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permissao [id=" + id + ", nome=" + nome + "]";
	}

}
