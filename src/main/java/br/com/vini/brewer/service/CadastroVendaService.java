package br.com.vini.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.model.StatusVenda;
import br.com.vini.brewer.model.Venda;
import br.com.vini.brewer.repository.VendaRepository;
import br.com.vini.brewer.service.event.venda.VendaEvent;

@Service
public class CadastroVendaService {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ApplicationEventPublisher publish;
	
	@Transactional
	public void salvar(Venda venda) {
		if(!venda.isSalvarPermitido()) {
			throw new RuntimeException("Usuário tentando salvar uma venda cancelada!");
		}
		
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHoraEntrega() != null ? venda.getHoraEntrega() : LocalTime.NOON));
		}
		
		if(!venda.isNova()) {
			Venda vendaExistente = vendaRepository.findOne(venda.getId());
			venda.setDataCriacao(vendaExistente.getDataCriacao());
			
			if(!(venda.getStatus().equals(StatusVenda.EMITIDA) && !venda.getStatus().equals(vendaExistente.getStatus()))) {
				venda.setStatus(vendaExistente.getStatus());
			}
		}
		
		this.vendaRepository.save(venda);
	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
		
		publish.publishEvent(new VendaEvent(venda));
	}

	@Transactional
	@PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")
	public void cancelar(Venda venda) {
		Venda vendaExistente = vendaRepository.findOne(venda.getId());
		vendaExistente.setStatus(StatusVenda.CANCELADA);
		this.vendaRepository.save(vendaExistente);
	}
}
