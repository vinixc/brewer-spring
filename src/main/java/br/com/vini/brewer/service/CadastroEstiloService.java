package br.com.vini.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.model.Estilo;
import br.com.vini.brewer.repository.EstiloRepository;

@Service
public class CadastroEstiloService {
	
	@Autowired
	private EstiloRepository estiloRepository;
	

	@Transactional
	public void salvar(Estilo estilo) {
		estiloRepository.save(estilo);
	}

}
