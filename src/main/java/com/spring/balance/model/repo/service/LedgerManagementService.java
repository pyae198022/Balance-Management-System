package com.spring.balance.model.repo.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.balance.controller.member.dto.LedgerForm;
import com.spring.balance.controller.member.dto.LedgerListItem;
import com.spring.balance.controller.member.dto.LedgerSearch;
import com.spring.balance.controller.member.dto.LedgerSelectItem;
import com.spring.balance.model.PageResult;
import com.spring.balance.model.entity.Account_;
import com.spring.balance.model.entity.Ledger;
import com.spring.balance.model.entity.Ledger_;
import com.spring.balance.model.entity.Member_;
import com.spring.balance.model.entity.consts.BalanceType;
import com.spring.balance.model.repo.LedgerRepo;
import com.spring.balance.model.repo.MemberRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerManagementService {

    private final MemberRepo memberRepo;
	
	private final LedgerRepo ledgerRepo;
	
	@PreAuthorize("authentication.name eq #username")
	public PageResult<LedgerListItem> search(String username, LedgerSearch form, int page, int size) {
		
		return ledgerRepo.search(queryFunc(username, form), countFunc(username, form), page, size);
	}

	@Transactional
	public void save(LedgerForm ledgerForm) {
		
		var entity = Optional.ofNullable(ledgerForm.getId())
				.flatMap(id -> ledgerRepo.findById(id))
				.orElse(new Ledger());
		
		entity.setName(ledgerForm.getName());
		entity.setDeleted(ledgerForm.getStatus());
		entity.setBalanceType(ledgerForm.getType());
		
		if(entity.getMember() == null) {
			var username = SecurityContextHolder.getContext()
					.getAuthentication().getName();
			
			entity.setMember(memberRepo.findOneByAccountUsername(username).orElseThrow());
		}
		
		ledgerRepo.save(entity);
	}

	public LedgerForm findForEdit(Integer id) {
		return ledgerRepo.findById(id).map(LedgerForm::from).orElse(new LedgerForm());
	}
	
	
	private Function<CriteriaBuilder, CriteriaQuery<LedgerListItem>> queryFunc(String username, LedgerSearch form) {
		
		return cb -> {
			var cq = cb.createQuery(LedgerListItem.class);
			var root = cq.from(Ledger.class);
			
			LedgerListItem.select(cb, cq, root);
			cq.where(form.where(username, cb, root));
			
			cq.orderBy(
					cb.asc(root.get(Ledger_.ID)));
			
			return cq;
		};
	}
	

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(String username, LedgerSearch form) {

		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Ledger.class);
			
			cq.select(cb.count(root.get(Ledger_.ID)));
			cq.where(form.where(username, cb, root));
			
			return cq;
		};
	}

	@PreAuthorize("authentication.name eq #username")
	public List<LedgerSelectItem> findForEntry(String username, BalanceType type) {
		
		return ledgerRepo.search(queryFunc(username, type));
	}

	private Function<CriteriaBuilder, CriteriaQuery<LedgerSelectItem>> queryFunc(String username, BalanceType type) {
		
		return cb -> {
			var cq = cb.createQuery(LedgerSelectItem.class);
			var root = cq.from(Ledger.class);
			
			cq.multiselect(
					root.get(Ledger_.ID),
					root.get(Ledger_.NAME)
			);
			
			cq.where(
					cb.equal(root.get(Ledger_.MEMBER).get(Member_.ACCOUNT).get(Account_.USERNAME), username),
					cb.equal(root.get(Ledger_.BALANCE_TYPE), type)
			);
			
			cq.orderBy(cb.asc(root.get(Ledger_.NAME)));
			return cq;
		};
	}
	
}
