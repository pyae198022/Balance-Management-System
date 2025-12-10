package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record LedgerEntryDetails(
		String code,
		String ledgerName,
		BigDecimal amount,
		LocalDateTime issuteAt,
		String particular,
		List<LedgerEntryDetailsItem> items
		) {

}
