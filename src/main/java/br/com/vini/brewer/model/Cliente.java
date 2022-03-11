package br.com.vini.brewer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import br.com.vini.brewer.model.validation.ClienteGroupSequenceProvider;
import br.com.vini.brewer.model.validation.group.CnpjGroup;
import br.com.vini.brewer.model.validation.group.CpfGroup;

@Entity
@Table(name = "cliente")
@SequenceGenerator(allocationSize = 1,initialValue = 1, name = "cliente_seq", sequenceName = "cliente_seq")
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente implements Serializable{
	private static final long serialVersionUID = -7500292048102423872L;
	
	@Id
	@GeneratedValue(generator = "cliente_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Size(max = 80,min = 5,message = "nome deve ter entre 5 e 80 caracteres")
	@NotBlank(message = "nome é obrigatorio")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa")
	@NotNull(message = "Tipo pessoa é obrigatorio")
	private TipoPessoa tipoPessoa;
	
	@Column(name = "cpf_cnpj")
	@Size(max = 30, message = "cpf ou cnpj deve ter no máximo 30 caracteres")
	@NotBlank(message = "cpf ou cnpj é obrigatorio")
	@CPF(groups = CpfGroup.class)
	@CNPJ(groups = CnpjGroup.class)
	private String cpfOuCnpj;
	
	@Size(max = 20, message = "telefone deve ter no máximo 20 caracteres")
	private String telefone;
	
	@Email(message = "e-mail invalido")
	@Size(max = 50, message = "email deve ter no máximo 50 caracteres")
	private String email;
	
	@Embedded
	private Endereco endereco;
	
	@PrePersist @PreUpdate
	private void preInsertPreUpdate() {
		this.cpfOuCnpj = getCpfOuCnpjSemFormatacao();
	}
	
	@PostLoad
	private void postLoad() {
		this.cpfOuCnpj = this.tipoPessoa.formatar(this.cpfOuCnpj);
	}

	public Cliente() {}

	public Cliente(String nome, TipoPessoa tipoPessoa, String cpfOuCnpj, String telefone, String email,
			Endereco endereco) {
		this.nome = nome;
		this.tipoPessoa = tipoPessoa;
		this.cpfOuCnpj = cpfOuCnpj;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}
	
	public String getCpfOuCnpjSemFormatacao() {
		return TipoPessoa.removerFormatacao(this.cpfOuCnpj);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", tipoPessoa=" + tipoPessoa + ", cpfOuCnpj=" + cpfOuCnpj + "]";
	}
	
}
