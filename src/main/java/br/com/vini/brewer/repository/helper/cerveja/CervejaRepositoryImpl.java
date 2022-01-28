package br.com.vini.brewer.repository.helper.cerveja;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.filter.CervejaFilter;

public class CervejaRepositoryImpl implements CervejaRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		
		int totalRegistersForpage = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int firstRegister = currentPage * totalRegistersForpage;
				
		criteria.setFirstResult(firstRegister);
		criteria.setMaxResults(totalRegistersForpage);
		
		adicionaOrdenacao(pageable, criteria);
		
		if(filter != null) {
			adicionaRestricoes(filter, criteria);
		}
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}

	private Long total(CervejaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		
		if(filter != null) {
			adicionaRestricoes(filter, criteria);
		}
		
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	private boolean isEstiloPresent(CervejaFilter filter) {
		return filter.getEstilo() != null && filter.getEstilo().getId() != null;
	}
	
	private void adicionaOrdenacao(Pageable pageable, Criteria criteria) {
		Sort sort = pageable.getSort();
		if(sort != null) {
			Sort.Order order = sort.iterator().next();
			String field = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(field) : Order.desc(field));
		}
	}
	
	private void adicionaRestricoes(CervejaFilter filter, Criteria criteria) {
		if(!StringUtils.isEmpty(filter.getSku())) {
			criteria.add(Restrictions.ilike("sku",  "%" + filter.getSku() + "%"));
		}
		
		if(!StringUtils.isEmpty(filter.getNome())) {
			criteria.add(Restrictions.ilike("nome",filter.getNome(), MatchMode.ANYWHERE));
		}
		
		if(isEstiloPresent(filter)) {
			criteria.add(Restrictions.eq("estilo", filter.getEstilo()));
		}
		
		if(filter.getSabor() != null) {
			criteria.add(Restrictions.eq("sabor", filter.getSabor()));
		}
		
		if(filter.getOrigem() != null) {
			criteria.add(Restrictions.eq("origem", filter.getOrigem()));
		}
		
		if(filter.getValorDe() != null) {
			criteria.add(Restrictions.ge("valor", filter.getValorDe()));
		}
		
		if(filter.getValorAte() != null) {
			criteria.add(Restrictions.le("valor", filter.getValorAte()));
		}
	}

}
