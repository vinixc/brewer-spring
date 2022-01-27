package br.com.vini.brewer.repository.helper.cerveja;

import java.util.List;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.filter.CervejaFilter;

public interface CervejaRepositoryQuery {
	
	public List<Cerveja> filtrar(CervejaFilter filter);

}
