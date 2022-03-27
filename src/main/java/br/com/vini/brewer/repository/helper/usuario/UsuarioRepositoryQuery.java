package br.com.vini.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.vini.brewer.model.Usuario;
import br.com.vini.brewer.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {
	
	Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);
	Optional<Usuario> porEmailEAtivo(String email);
	List<String> permissoes(Usuario usuario);
	
	Usuario buscarComGrupos(Long codigo);

}
