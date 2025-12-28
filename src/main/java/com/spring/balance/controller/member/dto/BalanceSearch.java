package com.spring.balance.controller.member.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import com.spring.balance.model.entity.Account_;
import com.spring.balance.model.entity.LedgerEntry;
import com.spring.balance.model.entity.LedgerEntry_;
import com.spring.balance.model.entity.Ledger_;
import com.spring.balance.model.entity.Member_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class BalanceSearch {

	private LocalDate dateFrom;
	private LocalDate dateTo;
	
	public Predicate[] where(String username, CriteriaBuilder cb, Root<LedgerEntry> root) {
		
		var params = new ArrayList<Predicate>();
		
		params.add(cb.equal(root.get(LedgerEntry_.ledger)
				.get(Ledger_.member).get(Member_.account).get(Account_.username), username));
		
		if(null != dateFrom) {
			params.add(cb.greaterThanOrEqualTo(root.get(LedgerEntry_.issueAt), dateFrom.atStartOfDay()));
		}
		
		if(null != dateTo) {
			params.add(cb.lessThan(root.get(LedgerEntry_.issueAt), dateFrom.plusDays(1).atStartOfDay()));
		}
		
		
		return params.toArray(size -> new Predicate[size]);
	}
	
}
