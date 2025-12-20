package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.spring.balance.model.entity.LedgerEntry;
import com.spring.balance.model.entity.LedgerEntry_;
import com.spring.balance.model.entity.Ledger_;
import com.spring.balance.model.entity.embaddables.LedgerEntryPK;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record LedgerEntryListItem(
		String code,
		LocalDateTime issueAt,
		String ledgerName,
		String particular,
		BigDecimal amount
		) {

	public LedgerEntryListItem(LedgerEntryPK id, LocalDateTime issueAt, String ledgerName, String particular,
					BigDecimal amount) {
		this(id.getCode(), issueAt, ledgerName, particular, amount);
	}
	
	public static void select(CriteriaQuery<LedgerEntryListItem> cq, CriteriaBuilder cb, Root<LedgerEntry> root) {
		cq.multiselect(
				root.get(Ledger_.ID),
				root.get(LedgerEntry_.ISSUE_AT),
				root.get(LedgerEntry_.LEDGER).get(Ledger_.NAME),
				root.get(LedgerEntry_.PARTICULAR),
		        root.get(LedgerEntry_.AMOUNT)
				);
		
	}

}
