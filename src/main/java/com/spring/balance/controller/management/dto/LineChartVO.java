package com.spring.balance.controller.management.dto;

import java.time.LocalDate;

public record LineChartVO(
		LocalDate date,
		Long value
		) {

}
