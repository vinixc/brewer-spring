package br.com.vini.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.CervejaRepository;
import br.com.vini.brewer.service.event.cerveja.CervejaSalvaEvent;

@Service
public class CadastroCervejaService {
	
	@Autowired
	private CervejaRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void salvar(Cerveja cerveja) {
		repository.save(cerveja);
		
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}
}
