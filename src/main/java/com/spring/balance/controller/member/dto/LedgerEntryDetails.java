package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.spring.balance.model.entity.LedgerEntry;
import com.spring.balance.model.entity.consts.BalanceType;

public record LedgerEntryDetails(
		String code,
		BalanceType type,
		String ledgerName,
		BigDecimal amount,
		LocalDateTime issuteAt,
		String particular,
		List<LedgerEntryDetailsItem> items
		) {

	public BigDecimal getTotal() {
		return items.stream().map(a -> a.getTotal())
				.reduce((a, b) -> a.add(b)).orElse(BigDecimal.ZERO);
	}
	
	public static LedgerEntryDetails from(LedgerEntry entry) {
		return new LedgerEntryDetails(
				entry.getId().getCode(),
				entry.getLedger().getBalanceType(),
				entry.getLedger().getName(),
				entry.getAmount(),
				entry.getIssueAt(),
				entry.getParticular(),
				entry.getItems().stream()
				.map(LedgerEntryDetailsItem::from).toList()
			);
	}
}
