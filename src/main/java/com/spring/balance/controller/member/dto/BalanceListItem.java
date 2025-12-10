package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BalanceListItem(
		String code,
		LocalDateTime issuteAt,
		String ledgerName,
		String particular,
		BigDecimal income,
		BigDecimal expense,
		BigDecimal balance
		) {

}
