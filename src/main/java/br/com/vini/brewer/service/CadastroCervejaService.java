package br.com.vini.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.exception.ImpossivelExcluirEntidadeException;
import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.CervejaRepository;
import br.com.vini.brewer.service.event.cerveja.CervejaSalvaEvent;
import br.com.vini.brewer.storage.FotoStorage;

@Service
public class CadastroCervejaService {
	
	@Autowired
	private CervejaRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private FotoStorage fotoStorage;
	
	@Transactional
	public void salvar(Cerveja cerveja) {
		repository.save(cerveja);
		
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}

	@Transactional
	public void excluir(Cerveja cerveja) {
		
		try {
			
			String nomeFoto = cerveja.getFoto();
			repository.delete(cerveja);
			repository.flush();
			fotoStorage.excluir(nomeFoto);
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cerveja. Já foi usada em alguma venda.");
		}
		
	}
}
