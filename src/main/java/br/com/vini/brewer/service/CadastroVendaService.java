package br.com.vini.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.model.StatusVenda;
import br.com.vini.brewer.model.Venda;
import br.com.vini.brewer.repository.VendaRepository;

@Service
public class CadastroVendaService {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Transactional
	public void salvar(Venda venda) {
		
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHoraEntrega() != null ? venda.getHoraEntrega() : LocalTime.NOON));
		}
		
		this.vendaRepository.save(venda);
	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
	}
}
