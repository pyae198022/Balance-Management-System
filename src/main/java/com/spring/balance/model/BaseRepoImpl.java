package com.spring.balance.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class BaseRepoImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepo<T, ID>{
	
	private EntityManager manager;

	public BaseRepoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.manager = entityManager;
	}

	@Override
	public <R> List<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFun) {
		return manager.createQuery(queryFun.apply(manager.getCriteriaBuilder())).getResultList();
	}

	@Override
	public <R> PageResult<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFun,
			Function<CriteriaBuilder, CriteriaQuery<Long>> countFun, int page, int size) {
		
		var count = count(countFun);
		
		var criteriaQuery = queryFun.apply(manager.getCriteriaBuilder());
		var jpaQuery = manager.createQuery(criteriaQuery);
		
		jpaQuery.setMaxResults(size);
		jpaQuery.setFirstResult(size * page);
		var list = jpaQuery.getResultList();
		PageResult.Builder<R> builder = PageResult.builder();
		
		return builder.contents(list)
				.count(count)
				.size(size)
				.page(page)
				.build();
	}

	@Override
	public <R> Optional<R> searchOne(Function<CriteriaBuilder, CriteriaQuery<R>> queryFun) {
		return manager.createQuery(queryFun.apply(manager.getCriteriaBuilder())).getResultList().stream().findAny();
	}

	@Override
	public Long count(Function<CriteriaBuilder, CriteriaQuery<Long>> queryFun) {
		return manager.createQuery(queryFun.apply(manager.getCriteriaBuilder())).getSingleResult();
	}

}
