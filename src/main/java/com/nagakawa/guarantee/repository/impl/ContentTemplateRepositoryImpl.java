package com.nagakawa.guarantee.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Pageable;
import com.nagakawa.guarantee.model.ContentTemplate;
import com.nagakawa.guarantee.repository.extend.ContentTemplateRepositoryExtend;

public class ContentTemplateRepositoryImpl implements ContentTemplateRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ContentTemplate> search(int status, Pageable pageable) {
		String hql = new String("FROM ContentTemplate e WHERE e.status != :status");

		Query query = this.entityManager.createQuery(hql, ContentTemplate.class);

		query.setParameter("status", status);

		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageNumber() * pageable.getPageSize() + pageable.getPageSize());

		return query.getResultList();
	}

	@Override
	public Long count(int status) {
		String hql = new String("SELECT count(1) FROM ContentTemplate e WHERE e.status != :status");

		Query query = this.entityManager.createQuery(hql);

		query.setParameter("status", status);

		return (Long) query.getSingleResult();
	}

}
