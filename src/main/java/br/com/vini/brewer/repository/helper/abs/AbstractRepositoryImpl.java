package br.com.vini.brewer.repository.helper.abs;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;

import org.hibernate.Criteria;
import org.hibernate.Session;
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
		
		Long total = total(filter);
		return new PageImpl<T>(criteria.list(), pageable, total);
	}
	
	protected Long total(Filter filter) {
		if(filter != null) {
			adicionaRestricoes(filter,criteriaCount);
		}
		
		criteriaCount.setProjection(Projections.rowCount());
		
		return (Long) criteriaCount.uniqueResult();
	}

	protected void adicionarRestricoesDePaginacao(Pageable pageable) {
		int totalRegistersForpage = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int firstRegister = currentPage * totalRegistersForpage;
				
		criteria.setFirstResult(firstRegister);
		criteria.setMaxResults(totalRegistersForpage);
	}
	
	protected void adicionaOrdenacao(Pageable pageable) {
		Sort sort = pageable.getSort();
		if(sort != null) {
			Sort.Order order = sort.iterator().next();
			String field = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(field) : Order.desc(field));
		}else {
			Type type = getType();
			try {
				List<Field> fields = Arrays.asList(Class.forName(type.getTypeName()).getDeclaredFields());
				Field fieldId = fields.stream()
					.filter(f -> f.isAnnotationPresent(Id.class))
					.findAny().orElse(null);
				
				if(fieldId != null) {
					criteria.addOrder(Order.desc(fieldId.getName()));
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	protected void initCriterias() {
		this.criteria = getCriteria();
		this.criteriaCount = getCriteria();
	}
	
	protected Criteria getCriteria() {
		Type type = getType();
		return getEntityManage().unwrap(Session.class).createCriteria(type.getTypeName());
	}
	
	/**
	 * Metodo utilizado para adicionar as restricoes utilizadas nos filtros da pesquisa
	 * Criar object de filter que implemente Filter 
	 * @param filter
	 * @param criteria
	 */
	protected  abstract void adicionaRestricoes(Filter filter,Criteria criteria);
	protected abstract EntityManager getEntityManage();
	
	private Type getType() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type type = genericSuperclass.getActualTypeArguments()[0];
		return type;
	}
}
