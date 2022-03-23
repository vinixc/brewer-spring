package br.com.vini.brewer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vini.brewer.model.ItemVenda;
import br.com.vini.brewer.model.Venda;
import br.com.vini.brewer.repository.VendaRepository;

@Service
public class CadastroVendaService {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Transactional
	public void salvar(Venda venda) {
		
		BigDecimal valorTotal = calcularValorTotalVenda(venda.getItens(), venda.getValorFrete(), venda.getValorDesconto());
		venda.setValorTotal(valorTotal);
		
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHoraEntrega()));
		}
		
		this.vendaRepository.save(venda);
	}

	private BigDecimal calcularValorTotalVenda(List<ItemVenda> itens, BigDecimal valorFrete, BigDecimal valorDesconto) {
		
		BigDecimal valorTotalItens = itens
				.stream().map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.get();
		
		BigDecimal valorTotal = valorTotalItens
				.add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
				.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
		
		return valorTotal;
	}

}
