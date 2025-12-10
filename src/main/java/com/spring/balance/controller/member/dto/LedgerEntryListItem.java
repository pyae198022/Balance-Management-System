package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LedgerEntryListItem(
		String code,
		LocalDateTime issueAt,
		String ledgerName,
		String particular,
		BigDecimal amount
		) {

}
