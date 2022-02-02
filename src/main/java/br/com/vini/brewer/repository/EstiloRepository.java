package br.com.vini.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vini.brewer.model.Estilo;
import br.com.vini.brewer.repository.helper.estilo.EstiloRepositoryQuery;

public interface EstiloRepository extends JpaRepository<Estilo, Long>, EstiloRepositoryQuery{
	
	public Optional<Estilo> findByNomeIgnoreCase(String nome);

}
