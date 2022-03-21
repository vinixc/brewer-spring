package br.com.vini.brewer.venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.model.ItemVenda;

public class TabelaItensVenda {
	
	private List<ItemVenda> itens = new ArrayList<>();
	
	public BigDecimal getValorTotal() {
		return itens.stream()
			.map(ItemVenda::getValorTotal)
			.reduce(new BigDecimal(0), (i1,i2) -> i1.add(i2));
	}
	
	public void adicionarItem(Cerveja cerveja, Integer quantidade) {
		ItemVenda itemVenda = new ItemVenda();
		itemVenda.setCerveja(cerveja);
		itemVenda.setQuantidade(quantidade);
		itemVenda.setValorUnitario(cerveja.getValor());
		
		itens.add(itemVenda);
	}
				
	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}

}
