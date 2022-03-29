package br.com.vini.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vini.brewer.model.Cliente;
import br.com.vini.brewer.repository.helper.cliente.ClienteRepositoryQuery;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery{

	Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

	List<Cliente> findByNomeStartingWithIgnoreCase(String nome);
}
