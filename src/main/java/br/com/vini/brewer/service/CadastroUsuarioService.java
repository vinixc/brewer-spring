package br.com.vini.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.exception.EmailJaCadastradoException;
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

		Optional<Usuario> emailJaCadastrado = repository.findByEmail(usuario.getEmail());
		if(emailJaCadastrado.isPresent()) {
			throw new EmailJaCadastradoException("E-mail já cadastrado!");
		}
		
		if(usuario.isNovo()) {
			usuario.criptografaPassword(passwordEncoder);
		}
		
		repository.save(usuario);
	}

	@Transactional
	public void alterarStatus(Long[] ids, StatusUsuario status) {
		status.executar(ids, repository);
	}
}
