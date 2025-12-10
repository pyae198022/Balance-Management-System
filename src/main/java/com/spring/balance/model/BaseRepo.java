package com.spring.balance.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@NoRepositoryBean
public interface BaseRepo<T, ID> extends JpaRepository<T, ID>{

	<R> List<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFun);
	
	<R> PageResult<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFun,
			Function<CriteriaBuilder, CriteriaQuery<Long>> countFun, int page, int size
			);
	
	<R>Optional<R> searchOne(Function<CriteriaBuilder, CriteriaQuery<R>> queryFun);
	
	Long count(Function<CriteriaBuilder, CriteriaQuery<Long>> queryFun);
}
