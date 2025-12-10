package com.spring.balance.controller.management.dto;

import java.time.LocalDateTime;

import com.spring.balance.model.entity.Member;
import com.spring.balance.model.entity.MemberActivity_;
import com.spring.balance.model.entity.Member_;
import com.spring.balance.model.entity.consts.MemberStatus;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record MemberListItem(
		long id,
		String name,
		LocalDateTime registeredAt,
		LocalDateTime lastLoginAt,
		MemberStatus status,
		LocalDateTime changedAt,
		String remark
		) {

	public static void select(CriteriaQuery<MemberListItem> cq, Root<Member> root) {
		cq.multiselect(
				root.get(Member_.ID),
				root.get(Member_.NAME),
				root.get(Member_.ACTIVITY).get(MemberActivity_.REGISTERED_AT),
				root.get(Member_.ACTIVITY).get(MemberActivity_.LAST_ACCESS_AT),
				root.get(Member_.ACTIVITY).get(MemberActivity_.MEMBER_STATUS),
				root.get(Member_.ACTIVITY).get(MemberActivity_.UPDATE_AT),
				root.get(Member_.ACTIVITY).get(MemberActivity_.STATUS_CHANGE_REASON)
				);
		
	}

}
