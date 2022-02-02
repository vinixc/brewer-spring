package br.com.vini.brewer.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.vini.brewer.model.Estilo;
import br.com.vini.brewer.repository.filter.EstiloFilter;

public interface EstiloRepositoryQuery {
	
	Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable);

}
