package com.spring.balance.model.repo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.balance.controller.member.dto.CharSummaryVO;
import com.spring.balance.controller.member.dto.ChartAmountVO;
import com.spring.balance.controller.member.dto.ChartBalanceVO;
import com.spring.balance.model.entity.consts.BalanceType;
import com.spring.balance.model.repo.LedgerEntryRepo;
import com.spring.balance.utils.LoadType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberChartService {
	
	private final LedgerEntryRepo entryRepo;
	
	public CharSummaryVO getSummaryData(LoadType type) {
		return switch(type) {
		case Monthly -> getSummaryDataForMonth();
		case Yearly -> getSummaryDataForYear();
		};
	}

	private CharSummaryVO getSummaryDataForYear() {
		
		var dateTo = LocalDate.now();
		var dateFrom = dateTo.minusYears(1);
		
		var incomes = getSummaryData(BalanceType.Incomes, dateFrom, dateTo);
		var expenses = getSummaryData(BalanceType.Expenses, dateFrom, dateTo);
		
		return new CharSummaryVO(getValue(incomes), getValue(expenses));
	}

	private CharSummaryVO getSummaryDataForMonth() {

		
		var dateTo = LocalDate.now();
		var dateFrom = dateTo.minusMonths(1);
		
		var incomes = getSummaryData(BalanceType.Incomes, dateFrom, dateTo);
		var expenses = getSummaryData(BalanceType.Expenses, dateFrom, dateTo);
		
		return new CharSummaryVO(getValue(incomes), getValue(expenses));
	}
	
	
	public List<ChartBalanceVO> getBalanceData(LoadType type) {
		
		return switch(type) {
		case Monthly -> getBalanceDataForMonth();
		case Yearly -> getBalanceDataForYear();
		
		};
	}

	private List<ChartBalanceVO> getBalanceDataForYear() {

		var result = new ArrayList<ChartBalanceVO>();
		var fromDate = LocalDate.now().minusYears(1);
		

		while(fromDate.compareTo(LocalDate.now()) < 0) {
			var toDate = fromDate.plusMonths(3);
			
			if(fromDate.compareTo(toDate) <= 0) {
				var incomes = getSummaryData(BalanceType.Incomes, fromDate, toDate);
				var expenses = getSummaryData(BalanceType.Expenses, fromDate, toDate);
				result.add(new ChartBalanceVO(fromDate, getValue(expenses), getValue(incomes)));
			}
			
			
			
			fromDate = toDate.plusDays(1);
		}
		
		return result;
	}

	private List<ChartBalanceVO> getBalanceDataForMonth() {
		var result = new ArrayList<ChartBalanceVO>();
		var fromDate = LocalDate.now().minusYears(1);
		

		while(fromDate.compareTo(LocalDate.now()) <= 0) {
			var toDate = fromDate.plusWeeks(1);
			
			if(fromDate.compareTo(toDate) < 0) {
				var incomes = getSummaryData(BalanceType.Incomes, fromDate, toDate);
				var expenses = getSummaryData(BalanceType.Expenses, fromDate, toDate);
				result.add(new ChartBalanceVO(fromDate, getValue(expenses), getValue(incomes)));
			}
			
			fromDate = toDate.plusDays(1);
		}
		
		return result;
	}

	public Map<BalanceType, List<ChartAmountVO>> getLedgerData(LoadType type) {
		
		return switch(type) {
		case Monthly -> getLedgerDataForMonth();
		case Yearly -> getLedgerDataForYear();
		};
	}
	
	private Map<BalanceType, List<ChartAmountVO>> getLedgerDataForYear() {
		
		var toDate = LocalDate.now();
		var fromDate = toDate.minusYears(1);
		
		var result = new HashMap<BalanceType, List<ChartAmountVO>>();
		result.put(BalanceType.Incomes, getLedgerData(BalanceType.Incomes, fromDate, toDate));
		result.put(BalanceType.Expenses, getLedgerData(BalanceType.Expenses, fromDate, toDate));
		
		return result;
	}

	private Map<BalanceType, List<ChartAmountVO>> getLedgerDataForMonth() {
		
		var toDate = LocalDate.now();
		var fromDate = toDate.minusMonths(1);
		
		var result = new HashMap<BalanceType, List<ChartAmountVO>>();
		result.put(BalanceType.Incomes, getLedgerData(BalanceType.Incomes, fromDate, toDate));
		result.put(BalanceType.Expenses, getLedgerData(BalanceType.Expenses, fromDate, toDate));
		
		return result;
	}
	
	private List<ChartAmountVO> getLedgerData(BalanceType type, LocalDate from, LocalDate to) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		var list =  entryRepo.getLedgerData(username, type, from, to);
		
		return list.stream().filter(a -> a.value().compareTo(BigDecimal.ZERO) > 0).toList();
	}

	private BigDecimal getSummaryData(BalanceType type, LocalDate from, LocalDate to) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return entryRepo.getSummaryData(username, type, from, to);
	}
	
	public BigDecimal getValue(BigDecimal value) {
		return Optional.ofNullable(value).orElse(BigDecimal.ZERO);
	}

}
