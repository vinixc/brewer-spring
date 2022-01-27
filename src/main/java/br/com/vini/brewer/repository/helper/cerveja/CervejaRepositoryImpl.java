package br.com.vini.brewer.repository.helper.cerveja;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
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
	public List<Cerveja> filtrar(CervejaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		
		if(filter != null) {
			
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
		
		return criteria.list();
	}

	private boolean isEstiloPresent(CervejaFilter filter) {
		return filter.getEstilo() != null && filter.getEstilo().getId() != null;
	}

}
