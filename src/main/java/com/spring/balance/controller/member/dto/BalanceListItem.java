package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.spring.balance.model.entity.Ledger;
import com.spring.balance.model.entity.LedgerEntry;
import com.spring.balance.model.entity.LedgerEntry_;
import com.spring.balance.model.entity.consts.BalanceType;
import com.spring.balance.model.entity.embaddables.LedgerEntryPK;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record BalanceListItem(
		String code,
		BalanceType type,
		String ledgerName,
		LocalDateTime issuteAt,
		String particular,
		BigDecimal income,
		BigDecimal expense,
		BigDecimal balance
		) {
	
	public BalanceListItem(
			LedgerEntryPK id,
			Ledger ledger,
			LocalDateTime issueAt,
			String particular,
			BigDecimal amount,
			BigDecimal lastAmount
			) {
		
		this(
			id.getCode(),
			ledger.getBalanceType(),
			ledger.getName(),
			issueAt,
			particular,
			ledger.getBalanceType() == BalanceType.Incomes ? amount : BigDecimal.ZERO,
			ledger.getBalanceType() == BalanceType.Expenses ? amount : BigDecimal.ZERO,
			ledger.getBalanceType() == BalanceType.Incomes ? lastAmount.add(amount) : lastAmount.subtract(amount)
			);
	}

	public static void select(CriteriaQuery<BalanceListItem> cq, Root<LedgerEntry> root) {
		cq.multiselect(
				root.get(LedgerEntry_.id),
				root.get(LedgerEntry_.ledger),
				root.get(LedgerEntry_.issueAt),
				root.get(LedgerEntry_.particular),
				root.get(LedgerEntry_.amount),
				root.get(LedgerEntry_.lastAmount)
				);
	}
}
