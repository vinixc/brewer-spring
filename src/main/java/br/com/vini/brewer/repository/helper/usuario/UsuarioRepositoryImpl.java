package br.com.vini.brewer.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.Grupo;
import br.com.vini.brewer.model.Usuario;
import br.com.vini.brewer.model.UsuarioGrupo;
import br.com.vini.brewer.repository.filter.Filter;
import br.com.vini.brewer.repository.filter.UsuarioFilter;
import br.com.vini.brewer.repository.helper.abs.AbstractRepositoryImpl;

public class UsuarioRepositoryImpl extends AbstractRepositoryImpl<Usuario> implements UsuarioRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
		initCriterias();
		adicionarRestricoesDePaginacao(pageable);
		adicionaOrdenacao(pageable);
		
		if(usuarioFilter != null) {
			adicionaRestricoes(usuarioFilter,criteria);
		}
		
		Long total = total(usuarioFilter);
		
		//Inicializando grupos manualmente para evitar problema com a paginacao
		@SuppressWarnings("unchecked")
		List<Usuario> filtrados = criteria.list();
		filtrados.forEach(u -> Hibernate.initialize(u.getGrupos()));
		
		return new PageImpl<Usuario>(filtrados, pageable, total);
	}
	
	@Override
	protected void adicionaRestricoes(Filter filter, Criteria criteria) {
		UsuarioFilter usuarioFilter = (UsuarioFilter) filter;
		
		if(StringUtils.hasText(usuarioFilter.getNome())) {
			criteria.add(Restrictions.ilike("nome", usuarioFilter.getNome(),MatchMode.ANYWHERE));
		}
		
		if(StringUtils.hasText(usuarioFilter.getEmail())) {
			criteria.add(Restrictions.ilike("email", usuarioFilter.getEmail(),MatchMode.ANYWHERE));
		}
		
		if(!ObjectUtils.isEmpty(usuarioFilter.getAtivo())) {
			criteria.add(Restrictions.eq("ativo", usuarioFilter.getAtivo()));
		}
		
//		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		if(usuarioFilter.getGrupos() != null && !usuarioFilter.getGrupos().isEmpty()) {
			
			List<Criterion> subqueries = new ArrayList<>();
			usuarioFilter.getGrupos().stream().map(Grupo::getId).forEach(grupoId ->{
				DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
				dc.add(Restrictions.eq("id.grupo.id", grupoId));
				dc.setProjection(Projections.property("id.usuario"));
				
				subqueries.add(
						Subqueries.propertyIn("id", dc)
				);
				
			});
			
			Criterion[] criterions = new Criterion[subqueries.size()];
			criteria.add(Restrictions.and(subqueries.toArray(criterions)));
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

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager.createQuery("SELECT DISTINCT p.nome FROM Usuario u INNER JOIN u.grupos g INNER JOIN g.permissoes p WHERE u = :usuario", String.class)
					.setParameter("usuario", usuario)
					.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario buscarComGrupos(Long codigo) {
		Criteria criteria = getCriteria();
		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("id", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Usuario) criteria.uniqueResult();
	}
}
