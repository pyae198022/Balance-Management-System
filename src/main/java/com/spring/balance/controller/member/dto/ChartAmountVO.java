package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;

public record ChartAmountVO(
		String ledger,
		BigDecimal value
		) {
}
