package br.com.vini.brewer.repository.helper.abs;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.vini.brewer.repository.filter.Filter;

public abstract class AbstractRepositoryImpl<T>{
	
	protected Criteria criteria;
	protected Criteria criteriaCount;
	
	public AbstractRepositoryImpl() {}
	
	@SuppressWarnings("unchecked")
	public Page<T> filtrar(Filter filter, Pageable pageable) {
		initCriterias();
		adicionarRestricoesDePaginacao(pageable);
		adicionaOrdenacao(pageable);
		
		if(filter != null) {
			adicionaRestricoes(filter,criteria);
		}
		
		return new PageImpl<T>(criteria.list(), pageable, total(filter));
	}
	
	private Long total(Filter filter) {
		if(filter != null) {
			adicionaRestricoes(filter,criteriaCount);
		}
		
		criteriaCount.setProjection(Projections.rowCount());
		
		return (Long) criteriaCount.uniqueResult();
	}

	private void adicionarRestricoesDePaginacao(Pageable pageable) {
		int totalRegistersForpage = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int firstRegister = currentPage * totalRegistersForpage;
				
		criteria.setFirstResult(firstRegister);
		criteria.setMaxResults(totalRegistersForpage);
	}
	
	private void adicionaOrdenacao(Pageable pageable) {
		Sort sort = pageable.getSort();
		if(sort != null) {
			Sort.Order order = sort.iterator().next();
			String field = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(field) : Order.desc(field));
		}
	}
	
	public  abstract void adicionaRestricoes(Filter filter,Criteria criteria);

	public abstract void initCriterias();
}
