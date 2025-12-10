package com.spring.balance.model.repo.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.balance.controller.member.dto.LedgerEntryForm;
import com.spring.balance.controller.member.dto.LedgerEntryListItem;
import com.spring.balance.controller.member.dto.LedgerEntrySearch;
import com.spring.balance.model.PageResult;
import com.spring.balance.model.entity.consts.BalanceType;
import com.spring.balance.model.entity.embaddables.LedgerEntryPK;
import com.spring.balance.model.repo.LedgerEntryRepo;
import com.spring.balance.model.repo.MemberRepo;
import com.spring.balance.utils.EntityOperation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerEntryService {
	
	private final LedgerEntryRepo ledgerEntryRepo;
	private final MemberRepo memberRepo;
	
	public PageResult<LedgerEntryListItem> search(BalanceType type, LedgerEntrySearch search, String username) {
		
	
		
		return null;
	}

	public LedgerEntryForm findForEdit(String id) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var member = memberRepo.findOneByAccountUsername(username).get();
		
		var entryPK = LedgerEntryPK.parse(id, member.getId());
		
		return EntityOperation.safeCall(ledgerEntryRepo.findById(entryPK)
				.map(LedgerEntryForm::from)
				, "ledger entry", "entry id", id);
	}

}
