package com.spring.balance.controller.member;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.balance.controller.member.dto.CharSummaryVO;
import com.spring.balance.controller.member.dto.ChartAmountVO;
import com.spring.balance.controller.member.dto.ChartBalanceVO;
import com.spring.balance.model.entity.consts.BalanceType;
import com.spring.balance.model.repo.service.MemberChartService;
import com.spring.balance.utils.LoadType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("member/chart")
public class MemberChartApi {
	
	private final MemberChartService chartService;

	@GetMapping("summary")
	CharSummaryVO getSummaryData(@RequestParam(required = false, defaultValue = "Monthly") LoadType type) {
		return chartService.getSummaryData(type);
	}
	
	@GetMapping("balance")
	List<ChartBalanceVO> getBalanceData(@RequestParam(required = false, defaultValue = "Monthly") LoadType type) {
		
		return chartService.getBalanceData(type);
	}
	
	@GetMapping("ledger")
	Map<BalanceType, List<ChartAmountVO>> getLedgerData(@RequestParam(required = false, defaultValue = "Monthly") LoadType type) {
		
		return chartService.getLedgerData(type);
	}
}
