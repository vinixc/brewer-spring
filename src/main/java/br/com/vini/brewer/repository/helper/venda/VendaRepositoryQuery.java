package br.com.vini.brewer.repository.helper.venda;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.vini.brewer.model.Venda;
import br.com.vini.brewer.repository.filter.VendaFilter;

public interface VendaRepositoryQuery {
	
	public Page<Venda> filtrar(VendaFilter filter, Pageable pageable);
	
	Venda buscarComItens(Long codigo);
	
	public BigDecimal valorTotalNoAno();
	public BigDecimal valorTotalNoMes();
	public BigDecimal valorTickerMedioNoAno();

}
