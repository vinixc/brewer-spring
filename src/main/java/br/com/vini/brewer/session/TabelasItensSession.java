package br.com.vini.brewer.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.model.ItemVenda;

@Component
@SessionScope
public class TabelasItensSession {
	
	private Set<TabelaItensVenda> tabelas = new HashSet<>();

	public void adicionarItem(String uuid, Cerveja cerveja, Integer quantidade) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.adicionarItem(cerveja, quantidade);
		tabelas.add(tabela);
	}

	public void alterarQuantidadeItens(String uuid, Cerveja cerveja, Integer quantidade) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.alterarQuantidadeItens(cerveja, quantidade);
	}

	public void excluirItem(String uuid, Cerveja cerveja) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.excluirItem(cerveja);
	}

	public List<ItemVenda> getItens(String uuid) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		return tabela.getItens();
	}
	
	public Object getValorTotal(String uuid) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		return tabela.getValorTotal();
	}
	
	private TabelaItensVenda buscarTabelaPorUuid(String uuid) {
		TabelaItensVenda tabela = tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaItensVenda(uuid));
		return tabela;
	}
}
