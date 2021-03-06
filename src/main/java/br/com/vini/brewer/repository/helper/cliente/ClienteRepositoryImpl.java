package br.com.vini.brewer.repository.helper.cliente;

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
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
		
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
	@Transactional(readOnly = true)
	public Cliente carregarComCidade(Long id) {
		Criteria criteria = getCriteria();
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("id", id));
		Cliente cliente = (Cliente) criteria.uniqueResult();
		
		if(cliente.getEndereco() != null && cliente.getEndereco().getCidade() != null) {
			cliente.getEndereco().setEstado(cliente.getEndereco().getCidade().getEstado());
		}
		
		return cliente;
	}
	
	@Override
	protected EntityManager getEntityManage() {
		return manager;
	}
}
