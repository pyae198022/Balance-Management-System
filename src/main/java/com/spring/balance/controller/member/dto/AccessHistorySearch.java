package com.spring.balance.controller.member.dto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.spring.balance.model.entity.AccountHistory;
import com.spring.balance.model.entity.AccountHistory_;
import com.spring.balance.model.entity.consts.AccessStatus;
import com.spring.balance.model.entity.embaddables.AccessHistoryPK_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class AccessHistorySearch {

	private String username;
	private AccessStatus status;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private String keyword;
	
	public Predicate[] where(CriteriaBuilder cb, Root<AccountHistory> root) {
		
		var params = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(username)) {
			params.add(cb.equal(root.get(AccountHistory_.ID).get(AccessHistoryPK_.USERNAME), username));
		}
		
		if(null != status) {
			params.add(cb.equal(root.get(AccountHistory_.STATUS), status));
		}
		
		if(null != dateFrom) {
			var value = dateFrom.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			params.add(cb.greaterThanOrEqualTo(root.get(AccountHistory_.ID).get(AccessHistoryPK_.ACCESS_AT), value));
		}
		
		if(null != dateTo) {
			var value = dateTo.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			params.add(cb.lessThanOrEqualTo(root.get(AccountHistory_.ID).get(AccessHistoryPK_.ACCESS_AT), value));
		}
		
		if(StringUtils.hasLength(keyword)) {
			params.add(cb.like(cb.lower(root.get(AccountHistory_.REMARK)), keyword.toLowerCase().concat("%")));
		}
		return params.toArray(size -> new Predicate[size]);
	}
}
