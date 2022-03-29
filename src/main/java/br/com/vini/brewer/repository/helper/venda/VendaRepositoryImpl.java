package br.com.vini.brewer.repository.helper.venda;

import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import br.com.vini.brewer.model.Venda;
import br.com.vini.brewer.repository.filter.Filter;
import br.com.vini.brewer.repository.filter.VendaFilter;
import br.com.vini.brewer.repository.helper.abs.AbstractRepositoryImpl;

public class VendaRepositoryImpl extends AbstractRepositoryImpl<Venda> implements VendaRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;

	@Override
	@Transactional(readOnly = true)
	public Page<Venda> filtrar(VendaFilter filter, Pageable pageable) {
		return super.filtrar(filter, pageable);
	}

	@Override
	protected void adicionaRestricoes(Filter filter, Criteria criteria) {
		
		VendaFilter filtro = (VendaFilter) filter;
		
		if(!ObjectUtils.isEmpty(filtro.getId())) {
			criteria.add(Restrictions.eq("id", filtro.getId()));
		}
		
		if(!ObjectUtils.isEmpty(filtro.getStatus())) {
			criteria.add(Restrictions.eq("status", filtro.getStatus()));
		}
		
		if(!ObjectUtils.isEmpty(filtro.getDesde())) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDesde().atStartOfDay()));
		}
		
		if(!ObjectUtils.isEmpty(filtro.getAte())) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getAte().atTime(LocalTime.MAX)));
		}
		
		if(!ObjectUtils.isEmpty(filtro.getValorMinimo())) {
			criteria.add(Restrictions.ge("valorTotal", filtro.getValorMinimo()));
		}
		
		if(!ObjectUtils.isEmpty(filtro.getValorMaximo())) {
			criteria.add(Restrictions.le("valorTotal", filtro.getValorMaximo()));
		}
		
		if(filtro.hasSearchForClient()) {
			criteria.createAlias("cliente", "c");
			
			if(filtro.hasNomeCliente()) {
				criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
			}
			

			if(filtro.hasCpfOuCnpjCliente()) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", filtro.getCpfOuCnpjCliente()));
			}
		}
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public Venda buscarComItens(Long codigo) {
		Criteria criteria = getCriteria();
		criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("id", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Venda) criteria.uniqueResult();
	}

	@Override
	protected EntityManager getEntityManage() {
		return manager;
	}
	
}
