package br.com.vini.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vini.brewer.model.Venda;
import br.com.vini.brewer.repository.helper.venda.VendaRepositoryQuery;

public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQuery{

}
