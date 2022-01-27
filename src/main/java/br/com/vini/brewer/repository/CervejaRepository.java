package br.com.vini.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.helper.cerveja.CervejaRepositoryQuery;

@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long>, CervejaRepositoryQuery{
	
	public Optional<Cerveja> findBySkuIgnoreCase(String sku);

}
