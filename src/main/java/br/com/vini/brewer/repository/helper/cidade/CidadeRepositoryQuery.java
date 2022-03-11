package br.com.vini.brewer.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.vini.brewer.model.Cidade;
import br.com.vini.brewer.repository.filter.CidadeFilter;

public interface CidadeRepositoryQuery {
	
	Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable);

}
