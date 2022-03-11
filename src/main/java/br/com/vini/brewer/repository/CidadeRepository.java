package br.com.vini.brewer.repository;

import br.com.vini.brewer.model.Cidade;
import br.com.vini.brewer.model.Estado;
import br.com.vini.brewer.repository.helper.cidade.CidadeRepositoryQuery;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeRepositoryQuery{
	
	Collection<Cidade> findByEstado(Estado estado);
	Collection<Cidade> findByEstadoId(Long idEstado);
	Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);

}
