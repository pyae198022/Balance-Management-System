package com.spring.balance.controller.management.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.spring.balance.model.entity.Member;
import com.spring.balance.model.entity.MemberActivity_;
import com.spring.balance.model.entity.Member_;
import com.spring.balance.model.entity.consts.MemberStatus;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class MemberSearch {

	private MemberStatus status;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private String name;

	public Predicate[] where(CriteriaBuilder cb, Root<Member> root) {

		var params = new ArrayList<Predicate>();
		var activity = root.join(Member_.activity);

		if (null != status) {
			params.add(cb.equal(activity.get(MemberActivity_.MEMBER_STATUS), status));
		}

		if (null != dateFrom) {
			params.add(cb.greaterThanOrEqualTo(activity.get(MemberActivity_.registeredAt), dateFrom.atStartOfDay()));
		}

		if (null != dateTo) {
			params.add(cb.lessThan(activity.get(MemberActivity_.registeredAt), dateTo.plusDays(1).atStartOfDay()));
		}

		if (StringUtils.hasLength(name)) {
			params.add(cb.like(cb.lower(root.get(Member_.name)), name.toLowerCase().concat("%")));
		}

		return params.toArray(size -> new Predicate[size]);
	}

}
