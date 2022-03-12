package br.com.vini.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.exception.EmailJaCadastradoException;
import br.com.vini.brewer.model.Usuario;
import br.com.vini.brewer.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Transactional
	public void cadastrar(Usuario usuario) {

		Optional<Usuario> emailJaCadastrado = repository.findByEmail(usuario.getEmail());
		if(emailJaCadastrado.isPresent()) {
			throw new EmailJaCadastradoException("E-mail já cadastrado!");
		}
		
		repository.save(usuario);
	}
}
