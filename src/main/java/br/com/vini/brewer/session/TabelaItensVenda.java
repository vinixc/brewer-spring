package br.com.vini.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.model.ItemVenda;

@Component
@SessionScope
public class TabelaItensVenda {
	
	private List<ItemVenda> itens = new ArrayList<>();
	
	public BigDecimal getValorTotal() {
		return itens.stream()
			.map(ItemVenda::getValorTotal)
			.reduce(new BigDecimal(0), (i1,i2) -> i1.add(i2));
	}
	
	public void adicionarItem(Cerveja cerveja, Integer quantidade) {
		
		Optional<ItemVenda> itemVendaOptional = itens.stream()
			.filter(i -> i.getCerveja().equals(cerveja))
			.findAny();
		
		ItemVenda itemVenda = new ItemVenda();
		if(itemVendaOptional.isPresent()) {
			itemVenda = itemVendaOptional.get();
			itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
		}else {
			
			itemVenda.setCerveja(cerveja);
			itemVenda.setQuantidade(quantidade);
			itemVenda.setValorUnitario(cerveja.getValor());
			itens.add(0,itemVenda);
		}
	}
	
	public int getTotal() {
		return itens.size();
	}
				
	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}

}
