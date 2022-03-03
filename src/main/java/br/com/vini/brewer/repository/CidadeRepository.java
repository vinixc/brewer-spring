package br.com.vini.brewer.repository;

import br.com.vini.brewer.model.Cidade;
import br.com.vini.brewer.model.Estado;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	
	Collection<Cidade> findByEstado(Estado estado);
	Collection<Cidade> findByEstadoId(Long idEstado);

}
