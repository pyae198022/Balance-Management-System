package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChartBalanceVO(
		LocalDate date,
		BigDecimal expenses,
		BigDecimal incomes
		) {
}
