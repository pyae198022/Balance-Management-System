package com.spring.balance.controller.member;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.balance.controller.member.dto.LedgerForm;
import com.spring.balance.controller.member.dto.LedgerSearch;
import com.spring.balance.model.entity.consts.BalanceType;
import com.spring.balance.model.repo.service.LedgerManagementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("member/ledger")
public class MemberLedgerController {

	private final LedgerManagementService ledgerService;
	
	@GetMapping
	String index(
			ModelMap model,
			LedgerSearch form,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		
		var username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		
		model.put("result", ledgerService.search(username, form, page, size));
		
		return "member/ledgers/list";
	}
	
	@ModelAttribute("balanceTypes")
	BalanceType[] types() {
		return BalanceType.values();
	}
	
	@PostMapping
	String save(
			ModelMap model,
			@Validated @ModelAttribute("form") LedgerForm  ledgerForm,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "member/ledgers/list";
		}
		
		ledgerService.save(ledgerForm);
		
		return "redirect:/member/ledger";
	}
	
	@ModelAttribute("form")
	LedgerForm ledgerForm(@RequestParam(required = false) Integer id) {
		
		if(null != id) {
			return ledgerService.findForEdit(id);
		}
		return new LedgerForm();
	}
}
