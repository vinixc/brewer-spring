package br.com.vini.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vini.brewer.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

}
