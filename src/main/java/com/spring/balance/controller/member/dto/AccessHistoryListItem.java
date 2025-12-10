package com.spring.balance.controller.member.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.spring.balance.model.entity.AccountHistory;
import com.spring.balance.model.entity.AccountHistory_;
import com.spring.balance.model.entity.consts.AccessStatus;
import com.spring.balance.model.entity.consts.AccessType;
import com.spring.balance.model.entity.embaddables.AccessHistoryPK_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record AccessHistoryListItem(
		String member,
		Instant accessAt,
		AccessType type,
		AccessStatus status,
		String remark
		) {
	
	public LocalDateTime getAccessAtLocal() {
		return accessAt.atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static void select(CriteriaQuery<AccessHistoryListItem> cq, Root<AccountHistory> root) {
		cq.multiselect(
				root.get(AccountHistory_.ID).get(AccessHistoryPK_.USERNAME),
				root.get(AccountHistory_.ID).get(AccessHistoryPK_.ACCESS_AT),
				root.get(AccountHistory_.accessType),
				root.get(AccountHistory_.STATUS),
				root.get(AccountHistory_.REMARK)
				);
		
	}

}
