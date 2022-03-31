package br.com.vini.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.exception.CpfCnpjClienteJaCadastradoException;
import br.com.vini.brewer.exception.ImpossivelExcluirEntidadeException;
import br.com.vini.brewer.model.Cliente;
import br.com.vini.brewer.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	public void salvar(Cliente cliente) {
		
		Optional<Cliente> clienteExistente = clienteRepository.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		
		if(clienteExistente.isPresent() && cliente.isNovo()) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}
		
		this.clienteRepository.save(cliente);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			
			Cliente cliente = this.clienteRepository.findOne(id);
			this.clienteRepository.delete(cliente);
			this.clienteRepository.flush();
			
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não é possível excluir esse cliente! O mesmo já foi utilizado em sistema!");
		}
	}
}
