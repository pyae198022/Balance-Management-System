package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.spring.balance.model.entity.Ledger;
import com.spring.balance.model.entity.LedgerEntry_;
import com.spring.balance.model.entity.Ledger_;
import com.spring.balance.model.entity.consts.BalanceType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record LedgerListItem(
		int id,
		BalanceType type,
		String name,
		boolean status,
		LocalDateTime createdAt,
		LocalDateTime modifiedAt,
		BigDecimal total
		) {

	public static void select( CriteriaBuilder cb,CriteriaQuery<LedgerListItem> cq, Root<Ledger> root) {
		
		var entry = root.join(Ledger_.entry, JoinType.LEFT);
		
		cq.multiselect(
				root.get(Ledger_.ID),
				root.get(Ledger_.BALANCE_TYPE),
				root.get(Ledger_.NAME),
				root.get(Ledger_.DELETED),
				root.get(Ledger_.CREATED_AT),
				root.get(Ledger_.UPDATE_AT),
				cb.sum(entry.get(LedgerEntry_.AMOUNT))
				);
		
		cq.groupBy(root.get(Ledger_.ID),
				root.get(Ledger_.BALANCE_TYPE),
				root.get(Ledger_.NAME),
				root.get(Ledger_.BALANCE_TYPE),
				root.get(Ledger_.DELETED),
				root.get(Ledger_.CREATED_AT),
				root.get(Ledger_.UPDATE_AT));
	}

}
