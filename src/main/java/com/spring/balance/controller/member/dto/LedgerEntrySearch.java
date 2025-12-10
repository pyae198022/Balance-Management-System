package com.spring.balance.controller.member.dto;

import java.time.LocalDate;

import com.spring.balance.model.entity.consts.BalanceType;

import lombok.Data;

@Data
public class LedgerEntrySearch {

	private BalanceType type;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private String keyword;
}
