package br.com.vini.brewer.repository.helper.cerveja;

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

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.filter.CervejaFilter;
import br.com.vini.brewer.repository.filter.Filter;
import br.com.vini.brewer.repository.helper.abs.AbstractRepositoryImpl;

public class CervejaRepositoryImpl extends AbstractRepositoryImpl<Cerveja> implements CervejaRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable) {
		return super.filtrar(filter, pageable);
	}
	
	@Override
	protected void adicionaRestricoes(Filter filter, Criteria criteria) {
		CervejaFilter cervejaFilter = (CervejaFilter) filter;
		
		if(!StringUtils.isEmpty(cervejaFilter.getSku())) {
			criteria.add(Restrictions.ilike("sku",  "%" + cervejaFilter.getSku() + "%"));
		}
		
		if(!StringUtils.isEmpty(cervejaFilter.getNome())) {
			criteria.add(Restrictions.ilike("nome",cervejaFilter.getNome(), MatchMode.ANYWHERE));
		}
		
		if(isEstiloPresent(cervejaFilter)) {
			criteria.add(Restrictions.eq("estilo", cervejaFilter.getEstilo()));
		}
		
		if(cervejaFilter.getSabor() != null) {
			criteria.add(Restrictions.eq("sabor", cervejaFilter.getSabor()));
		}
		
		if(cervejaFilter.getOrigem() != null) {
			criteria.add(Restrictions.eq("origem", cervejaFilter.getOrigem()));
		}
		
		if(cervejaFilter.getValorDe() != null) {
			criteria.add(Restrictions.ge("valor", cervejaFilter.getValorDe()));
		}
		
		if(cervejaFilter.getValorAte() != null) {
			criteria.add(Restrictions.le("valor", cervejaFilter.getValorAte()));
		}
	}

	@Override
	protected void initCriterias() {
		criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		criteriaCount = manager.unwrap(Session.class).createCriteria(Cerveja.class);
	}
	
	private boolean isEstiloPresent(CervejaFilter filter) {
		return filter.getEstilo() != null && filter.getEstilo().getId() != null;
	}
}
