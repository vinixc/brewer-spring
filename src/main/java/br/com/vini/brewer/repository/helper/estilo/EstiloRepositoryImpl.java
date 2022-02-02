package br.com.vini.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.Estilo;
import br.com.vini.brewer.repository.filter.EstiloFilter;
import br.com.vini.brewer.repository.filter.Filter;
import br.com.vini.brewer.repository.helper.abs.AbstractRepositoryImpl;

public class EstiloRepositoryImpl extends AbstractRepositoryImpl<Estilo> implements EstiloRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void adicionaRestricoes(Filter filter) {
		
		if(filter != null) {
			EstiloFilter estiloFilter = (EstiloFilter) filter;
			
			if(!StringUtils.isEmpty(estiloFilter.getNome())) {
				this.criteria.add(Restrictions.ilike("nome",estiloFilter.getNome(), MatchMode.ANYWHERE));
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable) {
		super.criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		return super.filtrar(estiloFilter, pageable);
	}
}
