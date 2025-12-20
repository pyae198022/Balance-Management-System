package com.spring.balance.controller.member.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.spring.balance.model.entity.Account_;
import com.spring.balance.model.entity.LedgerEntry;
import com.spring.balance.model.entity.LedgerEntry_;
import com.spring.balance.model.entity.Ledger_;
import com.spring.balance.model.entity.Member_;
import com.spring.balance.model.entity.consts.BalanceType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class LedgerEntrySearch {

	private BalanceType type;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private String keyword;
	
	public Predicate[] where(CriteriaBuilder cb, Root<LedgerEntry> root, String username, BalanceType type2) {
		
		var params = new ArrayList<Predicate>();
		
		params.add(cb.equal((root.get(LedgerEntry_.LEDGER).get(Ledger_.MEMBER).get(Member_.ACCOUNT).get(Account_.USERNAME)), username));
		
		params.add(cb.equal(root.get(LedgerEntry_.LEDGER).get(Ledger_.BALANCE_TYPE), type));
		
		if (null != dateFrom) {
			params.add(cb.greaterThanOrEqualTo(root.get(LedgerEntry_.ISSUE_AT), dateFrom.atStartOfDay()));
		}
		
		if (null != dateTo) {
			params.add(cb.lessThanOrEqualTo(root.get(LedgerEntry_.ISSUE_AT), dateTo.plusDays(1).atStartOfDay()));
		}
		
		if (StringUtils.hasLength(keyword)) {
			params.add(cb.like(cb.lower(root.get(LedgerEntry_.PARTICULAR)), keyword.toLowerCase().concat("%")));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}
}
