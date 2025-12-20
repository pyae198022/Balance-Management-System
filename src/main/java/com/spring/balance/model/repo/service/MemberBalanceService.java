package com.spring.balance.model.repo.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.balance.controller.member.dto.LedgerEntryDetails;
import com.spring.balance.model.entity.embaddables.LedgerEntryPK;
import com.spring.balance.model.repo.LedgerEntryRepo;
import com.spring.balance.model.repo.MemberRepo;
import com.spring.balance.utils.EntityOperation;

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
		
		return EntityOperation.safeCall(entryRepo.findById(entryId)
				.map(LedgerEntryDetails::from)
				, "Ledger Etntry", "id", id);

	
	}

}
