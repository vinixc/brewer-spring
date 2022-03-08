package br.com.vini.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vini.brewer.model.Cliente;
import br.com.vini.brewer.repository.helper.cliente.ClienteRepositoryQuery;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery{

}
