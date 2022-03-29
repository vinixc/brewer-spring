package br.com.vini.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.exception.ImpossivelExcluirEntidadeException;
import br.com.vini.brewer.exception.NomeCidadeJaCadastradaException;
import br.com.vini.brewer.model.Cidade;
import br.com.vini.brewer.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		
		Optional<Cidade> cidadeCadastrada = this.cidadeRepository.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		
		if(cidadeCadastrada.isPresent()) {
			throw new NomeCidadeJaCadastradaException("Nome da cidade já cadastrada");
		}
		
		this.cidadeRepository.save(cidade);
		
		return cidade;
	}

	@Transactional
	public void excluir(Long id) {
		try {
			
			Cidade cidade = cidadeRepository.getOne(id);
			cidadeRepository.delete(cidade);
			cidadeRepository.flush();
			
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possivel exluir a cidade já utilizada em sistema!");
		}
	}
}
