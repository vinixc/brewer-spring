package br.com.vini.brewer.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.CervejaRepository;

@Component
public class VendaListener {
	
	@Autowired
	private CervejaRepository cervejaRepository;

	@EventListener
	public void vendaEmitida(VendaEvent vendaEvent) {
		
		vendaEvent.getVenda().getItens().forEach(item -> {
			Cerveja cerveja = cervejaRepository.findOne(item.getCerveja().getId());
			cerveja.informarRetiradaEstoque(item.getQuantidade());
			cervejaRepository.save(cerveja);
		});
	}
}
