package br.com.vini.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
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
	public void adicionaRestricoes(Filter filter,Criteria criteria) {
		
		if(filter != null) {
			EstiloFilter estiloFilter = (EstiloFilter) filter;
			
			if(!StringUtils.isEmpty(estiloFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome",estiloFilter.getNome(), MatchMode.ANYWHERE));
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable) {
		return super.filtrar(estiloFilter, pageable);
	}

	@Override
	public void initCriterias() {
		criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		criteriaCount = manager.unwrap(Session.class).createCriteria(Estilo.class);
	}
}
