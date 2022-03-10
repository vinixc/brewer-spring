package br.com.vini.brewer.repository.helper.cliente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.Cliente;
import br.com.vini.brewer.repository.filter.ClienteFilter;
import br.com.vini.brewer.repository.filter.Filter;
import br.com.vini.brewer.repository.helper.abs.AbstractRepositoryImpl;

public class ClienteRepositoryImpl extends AbstractRepositoryImpl<Cliente>  implements ClienteRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable) {
		return super.filtrar(clienteFilter, pageable);
	}

	@Override
	protected void adicionaRestricoes(Filter filter, Criteria criteria) {
		if(filter != null) {
			ClienteFilter clienteFilter = (ClienteFilter) filter;
			
			if(!StringUtils.isEmpty(clienteFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome",clienteFilter.getNome(), MatchMode.ANYWHERE));
			}
			
			if(!StringUtils.isEmpty(clienteFilter.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("cpfOuCnpj", clienteFilter.getCpfOuCnpj()));
			}
		}
	}

	@Override
	protected EntityManager getEntityManage() {
		return manager;
	}
}
