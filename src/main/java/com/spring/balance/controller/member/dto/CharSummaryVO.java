package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;
import java.util.Optional;

public record CharSummaryVO(
		BigDecimal incomes,
		BigDecimal expenses
		) {

	public BigDecimal getBalances() {
		return Optional.ofNullable(incomes).orElse(BigDecimal.ZERO)
				.subtract(Optional.ofNullable(expenses).orElse(BigDecimal.ZERO));
	}
}
