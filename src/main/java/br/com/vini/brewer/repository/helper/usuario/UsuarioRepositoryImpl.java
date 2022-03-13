package br.com.vini.brewer.repository.helper.usuario;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.Usuario;
import br.com.vini.brewer.repository.filter.Filter;
import br.com.vini.brewer.repository.filter.UsuarioFilter;
import br.com.vini.brewer.repository.helper.abs.AbstractRepositoryImpl;

public class UsuarioRepositoryImpl extends AbstractRepositoryImpl<Usuario> implements UsuarioRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
		return super.filtrar(usuarioFilter, pageable);
	}
	
	@Override
	protected void adicionaRestricoes(Filter filter, Criteria criteria) {
		
		UsuarioFilter usuarioFilter = (UsuarioFilter) filter;
		
		if(StringUtils.hasText(usuarioFilter.getEmail())) {
			criteria.add(Restrictions.eq("email", usuarioFilter.getEmail()));
		}
		
		if(!ObjectUtils.isEmpty(usuarioFilter.getAtivo())) {
			criteria.add(Restrictions.eq("ativo", usuarioFilter.getAtivo()));
		}
	}

	@Override
	protected EntityManager getEntityManage() {
		return manager;
	}

	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		return manager.createQuery("SELECT u FROM Usuario u where lower(u.email) = lower(:email) and u.ativo = true", Usuario.class)
				.setParameter("email", email)
				.getResultList().stream().findFirst();
	}
}
