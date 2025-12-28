package com.spring.balance.controller.member;

import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.balance.controller.member.dto.BalanceSearch;
import com.spring.balance.model.repo.service.MemberBalanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member/balance")
@RequiredArgsConstructor
public class MemberReportForBalanceController {

	private final MemberBalanceService balanceService;
	@GetMapping
	String index(ModelMap model,
			BalanceSearch search, 
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10")  int size) {
		
		var username = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
		
		model.put("result", balanceService.search(username, search, page, size));
		
		return "member/balance/list";
	}
	
	@GetMapping("{id}")
	String findById(@PathVariable String id, ModelMap model) {
		
		var details = balanceService.findById(id);
		model.put("details", details);
		var issueAt = details.issuteAt().toLocalDate();
		model.put("editEnable", LocalDate.now().equals(issueAt));
		
		return "member/balance/details";
	}
}
