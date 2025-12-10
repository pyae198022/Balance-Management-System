package com.spring.balance.controller.member.dto;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.spring.balance.model.entity.Account_;
import com.spring.balance.model.entity.Ledger;
import com.spring.balance.model.entity.Ledger_;
import com.spring.balance.model.entity.Member_;
import com.spring.balance.model.entity.consts.BalanceType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record LedgerSearch(
		BalanceType type,
		String keyword
		) {

	public Predicate[] where(String username, CriteriaBuilder cb, Root<Ledger> root) {
		
		var params = new ArrayList<Predicate>();
		
		params.add(cb.equal(root.get(Ledger_.MEMBER)
				.get(Member_.ACCOUNT)
				.get(Account_.USERNAME), username));
		
		
		if(null != type) {
			params.add(cb.equal(root.get(Ledger_.BALANCE_TYPE), type));
		}
		
		if(StringUtils.hasLength(keyword)) {
			params.add(cb.like(cb.lower(root.get(Ledger_.NAME)), keyword.toLowerCase().concat("%")));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}


}
