package br.com.vini.brewer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.vini.brewer.dto.CervejaDTO;
import br.com.vini.brewer.dto.ItensEstoqueDTO;
import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.filter.CervejaFilter;

public interface CervejaRepositoryQuery {
	
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);
	
	public List<CervejaDTO> porSkuOuNome(String skuOuNome);
	
	public ItensEstoqueDTO valorItensEstoque();

}
