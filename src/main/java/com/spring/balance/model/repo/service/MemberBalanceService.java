package com.spring.balance.model.repo.service;

import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.balance.controller.member.dto.BalanceListItem;
import com.spring.balance.controller.member.dto.BalanceSearch;
import com.spring.balance.controller.member.dto.LedgerEntryDetails;
import com.spring.balance.model.PageResult;
import com.spring.balance.model.entity.LedgerEntry;
import com.spring.balance.model.entity.LedgerEntry_;
import com.spring.balance.model.entity.embaddables.LedgerEntryPK;
import com.spring.balance.model.entity.embaddables.LedgerEntryPK_;
import com.spring.balance.model.repo.LedgerEntryRepo;
import com.spring.balance.model.repo.MemberRepo;
import com.spring.balance.utils.EntityOperation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberBalanceService {

	private final MemberRepo memberRepo;
	private final LedgerEntryRepo entryRepo;

	@Transactional(readOnly = true)
	public LedgerEntryDetails findById(String id) {

		var username = SecurityContextHolder.getContext().getAuthentication().getName();

		var member = memberRepo.findOneByAccountUsername(username).get();

		var entryId = LedgerEntryPK.parse(id, member.getId());

		return EntityOperation.safeCall(entryRepo.findById(entryId).map(LedgerEntryDetails::from), "Ledger Etntry",
				"id", id);

	}

	@PreAuthorize("authentication.name eq #username")
	public PageResult<BalanceListItem> search(String username, BalanceSearch search, int page, int size) {

		return entryRepo.search(queryFunc(username, search), countFunc(username, search), page, size);
	}

	private Function<CriteriaBuilder, CriteriaQuery<BalanceListItem>> queryFunc(String username, BalanceSearch search) {

		return cb -> {
			var cq = cb.createQuery(BalanceListItem.class);
			var root = cq.from(LedgerEntry.class);

			BalanceListItem.select(cq, root);
			cq.where(search.where(username, cb, root));
			cq.orderBy(cb.asc(root.get(LedgerEntry_.id).get(LedgerEntryPK_.ISSUE_DATE)));
			cq.orderBy(cb.asc(root.get(LedgerEntry_.id).get(LedgerEntryPK_.seqNumber)));
			
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(String username, BalanceSearch search) {

		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(LedgerEntry.class);

			cq.select(cb.count(root.get(LedgerEntry_.ID)));
			cq.where(search.where(username, cb, root));

			return cq;
		};
	}

}
