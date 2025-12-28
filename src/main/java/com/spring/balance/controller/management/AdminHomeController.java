package com.spring.balance.controller.management;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.balance.controller.management.dto.AdminHomeVO;
import com.spring.balance.controller.management.dto.LineChartVO;
import com.spring.balance.model.repo.service.AdminChartDataService;
import com.spring.balance.model.repo.service.MemberManagementService;
import com.spring.balance.utils.LoadType;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/home")
public class AdminHomeController {

	private final MemberManagementService managementService;
	private final AdminChartDataService chartDataService;
	
	@GetMapping
	String index(ModelMap model) {
		
		var lastMonth = managementService.findMemberCount(LocalDate.now().minusMonths(1));
		var lastYear = managementService.findMemberCount(LocalDate.now().minusYears(1));
		var totalMember = managementService.findMemberCount(null);
		
		model.put("vo", new AdminHomeVO(lastMonth, lastYear, totalMember));
		return "management/home";
	}
	
	@ResponseBody
	@GetMapping("load")
	List<LineChartVO> loadData(@RequestParam(required = false, defaultValue = "Monthly") LoadType type) {
		return chartDataService.load(type);
	}
	
}
