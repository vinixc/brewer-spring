package br.com.vini.brewer.repository.helper.cidade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.Cidade;
import br.com.vini.brewer.repository.filter.CidadeFilter;
import br.com.vini.brewer.repository.filter.Filter;
import br.com.vini.brewer.repository.helper.abs.AbstractRepositoryImpl;

public class CidadeRepositoryImpl extends AbstractRepositoryImpl<Cidade> implements CidadeRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable) {
		return super.filtrar(filter, pageable);
	}

	@Override
	protected void adicionaRestricoes(Filter filter, Criteria criteria) {
		
		CidadeFilter cidadeFilter = (CidadeFilter) filter;
		criteria.createAlias("estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		if(StringUtils.hasText(cidadeFilter.getNome())) {
			criteria.add(Restrictions.ilike("nome", cidadeFilter.getNome(), MatchMode.ANYWHERE));
		}
		
		if(StringUtils.hasText(cidadeFilter.getEstado())) {
			criteria.add(Restrictions.eq("e.nome", cidadeFilter.getEstado()));
		}
		
	}

	@Override
	protected EntityManager getEntityManage() {
		return manager;
	}

}
