package br.com.vini.brewer.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.vini.brewer.model.Cliente;
import br.com.vini.brewer.repository.filter.ClienteFilter;

public interface ClienteRepositoryQuery {
	
	Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable);

}
