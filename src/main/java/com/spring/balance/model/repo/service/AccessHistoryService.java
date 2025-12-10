package com.spring.balance.model.repo.service;

import java.time.Instant;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.balance.controller.member.dto.AccessHistoryListItem;
import com.spring.balance.controller.member.dto.AccessHistorySearch;
import com.spring.balance.model.PageResult;
import com.spring.balance.model.entity.AccountHistory;
import com.spring.balance.model.entity.AccountHistory_;
import com.spring.balance.model.entity.consts.AccessStatus;
import com.spring.balance.model.entity.consts.AccessType;
import com.spring.balance.model.entity.embaddables.AccessHistoryPK;
import com.spring.balance.model.entity.embaddables.AccessHistoryPK_;
import com.spring.balance.model.repo.AccountHistoryRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessHistoryService {

	private final AccountHistoryRepo accessHistoryRepo;

	@Transactional
	public void loginSuccess(String name) {
		createHistory(name, AccessType.Login, AccessStatus.Success);
		
	}
	
	@Transactional
	public void logoutSuccess(String name) {
		createHistory(name, AccessType.Logout, AccessStatus.Success);
		
	}
	
	@Transactional
	public void signUp(String username) {
		createHistory(username, AccessType.Signup, AccessStatus.Success);
		
	}
	
	@Transactional
	public void loginError(String name, String message) {
		createHistory(name, AccessType.Login, AccessStatus.Fail, message);
		
	}
	
	@Transactional(readOnly = true)
	public PageResult<AccessHistoryListItem> search(AccessHistorySearch form, int page, int size) {
		
		return accessHistoryRepo.search(queryFunc(form), countFunc(form), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(AccessHistorySearch form) {
		
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(AccountHistory.class);
			
			cq.select(cb.count(root.get(AccountHistory_.ID)));
			
			cq.where(form.where(cb, root));
			
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<AccessHistoryListItem>> queryFunc(AccessHistorySearch form) {
		
		return cb -> {
			var cq = cb.createQuery(AccessHistoryListItem.class);
			var root = cq.from(AccountHistory.class);
			
			AccessHistoryListItem.select(cq, root);
			
			cq.where(form.where(cb, root));
			cq.orderBy(cb.desc(root.get(AccountHistory_.ID).get(AccessHistoryPK_.ACCESS_AT)));
			
			
			return cq;
		};
	}

	private void createHistory(String username, AccessType type, AccessStatus status) {
		createHistory(username, type, status, null);
	}

	
	private void createHistory(String username, AccessType type, AccessStatus status, String message) {
		
		var id = new AccessHistoryPK();
		id.setAccessAt(Instant.now());
		id.setUsername(username);
		
		var history = new AccountHistory();
		history.setId(id);
		history.setStatus(status);
		history.setAccessType(type);
		history.setRemark(message);
		
		accessHistoryRepo.save(history);
	}


}
