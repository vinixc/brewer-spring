package br.com.vini.brewer.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.vini.brewer.validation.AtributoConfirmacao;

@Entity
@Table(name = "usuario")
@SequenceGenerator(allocationSize = 1,initialValue = 1,name = "usuario_seq", sequenceName = "usuario_seq")

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "Confirmação da senha não conferem", atributoParaValidarAlteracao = "id")
@DynamicUpdate
public class Usuario implements Serializable{
	private static final long serialVersionUID = -5190619349609726115L;
	
	@Id
	@GeneratedValue(generator = "usuario_seq",  strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatorio")
	private String nome;
	
	@Email(message = "E-mail invalido")
	@NotBlank(message = "E-mail é obrigatorio")
	private String email;
	
	private String senha;
	
	@Transient
	private String confirmacaoSenha;
	
	private Boolean ativo;
	
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@NotNull(message = "Selecione pelo menos um grupo")
	@Size(min = 1, message = "Informe ao menos 1 grupo")
	@ManyToMany
	@JoinTable(name = "usuario_grupo", 
	joinColumns = @JoinColumn(name="usuario_id"), 
	inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List<Grupo> grupos;
	
	@PrePersist
	private void onPersist() {
		if(this.ativo == null) this.ativo = false;
	}
	
	@PreUpdate
	private void preUpdate() {
		this.confirmacaoSenha = senha;
	}
	
	public Boolean isNovo() {
		return this.id == null;
	}
	
	public boolean isEdicao() {
		return !isNovo();
	}
	
	public void criptografaPassword(PasswordEncoder passwordEncoder) {
		this.senha = passwordEncoder.encode(this.senha);
		this.confirmacaoSenha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", ativo=" + ativo
				+ ", dataNascimento=" + dataNascimento + ", grupos=" + grupos + "]";
	}
}
