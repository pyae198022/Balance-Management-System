package com.spring.balance.model.repo.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.balance.controller.management.dto.LineChartVO;
import com.spring.balance.model.entity.AccountHistory;
import com.spring.balance.model.entity.AccountHistory_;
import com.spring.balance.model.entity.embaddables.AccessHistoryPK_;
import com.spring.balance.model.repo.AccountHistoryRepo;
import com.spring.balance.utils.LoadType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminChartDataService {

	private final AccountHistoryRepo repo;

	@Transactional(readOnly = true)
	public List<LineChartVO> load(LoadType type) {
		return switch(type) {
		case Monthly -> loadMonthlyData();
		case Yearly -> loadYearlyData();
		};
	}

	private List<LineChartVO> loadYearlyData() {
		var result = new ArrayList<LineChartVO>();
		var endMonth = YearMonth.now();
		var startMonth = endMonth.minusYears(1);
		
		while(startMonth.compareTo(endMonth) <= 0) {
			var date = startMonth.atDay(1);
			var from = date.atStartOfDay();
			var to = date.plusMonths(1).atStartOfDay();
			result.add(getData(date, from, to));
			startMonth = startMonth.plusMonths(1);
		}
		
		return result;
	}

	private List<LineChartVO> loadMonthlyData() {
		var result = new ArrayList<LineChartVO>();

		var endDate = LocalDate.now();
		var startDate = endDate.minusMonths(1);
		
		while(startDate.compareTo(endDate) <= 0) {
			result.add(getData(startDate, startDate.atStartOfDay(), startDate.plusDays(1).atStartOfDay()));
			startDate = startDate.plusDays(1);
		}
		
		return result;
	}

	private LineChartVO getData(LocalDate date, LocalDateTime from, LocalDateTime to) {
		
		Function<LocalDateTime, Instant> converter = dateTime -> dateTime.atZone(ZoneId.systemDefault())
				.toInstant();
		
		Function<CriteriaBuilder, CriteriaQuery<Long>> queryFunc = cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(AccountHistory.class);
			
			cq.select(cb.count(root.get(AccountHistory_.id)));
			var params = new ArrayList<Predicate>();
			params.add(cb.greaterThanOrEqualTo(root.get(AccountHistory_.id).get(AccessHistoryPK_.accessAt), converter.apply(from)));
			params.add(cb.lessThan(root.get(AccountHistory_.id).get(AccessHistoryPK_.accessAt), converter.apply(to)));
			
			cq.where(params.toArray(size -> new Predicate[size]));
			
			return cq;
		};
		
		var value = repo.count(queryFunc);
		
		return new LineChartVO(date, value);
	}
}