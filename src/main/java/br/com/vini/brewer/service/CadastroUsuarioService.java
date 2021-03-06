package br.com.vini.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.exception.EmailJaCadastradoException;
import br.com.vini.brewer.exception.ImpossivelExcluirEntidadeException;
import br.com.vini.brewer.model.Usuario;
import br.com.vini.brewer.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void cadastrar(Usuario usuario) {

		Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new EmailJaCadastradoException("E-mail já cadastrado!");
		}
		
		if(usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.criptografaPassword(passwordEncoder);
		}
		
		if(usuario.isEdicao() && StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioExistente.get().getSenha());
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		
		if(usuario.isEdicao() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		}
		
		repository.save(usuario);
	}

	@Transactional
	public void alterarStatus(Long[] ids, StatusUsuario status) {
		status.executar(ids, repository);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			Usuario usuario = repository.getOne(id);
			repository.delete(usuario);
			repository.flush();
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possivel deletar esse usuario! O mesmo já foi utilizado em sistema!");
		}
	}
}
