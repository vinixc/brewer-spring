package br.com.vini.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.CervejaRepository;

@Service
public class CadastroCervejaService {
	
	@Autowired
	private CervejaRepository repository;

	@Transactional
	public void salvar(Cerveja cerveja) {
		repository.save(cerveja);
	}
}
