package com.spring.balance.controller.member;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.balance.controller.member.dto.LedgerEntryForm;
import com.spring.balance.controller.member.dto.LedgerEntrySearch;
import com.spring.balance.controller.member.dto.LedgerSelectItem;
import com.spring.balance.model.entity.consts.BalanceType;
import com.spring.balance.model.repo.service.LedgerEntryService;
import com.spring.balance.model.repo.service.LedgerManagementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("member/entry/{type}")
public class MemberLedgerEntryController {
	
	private final LedgerEntryService ledgerEntryService;
	private final LedgerManagementService ledgerManagementService;

	@GetMapping
	String index(
			ModelMap model,
			@PathVariable BalanceType type,
			LedgerEntrySearch search,
			@RequestParam(required = false, defaultValue = "0")  int page,
			@RequestParam(required = false, defaultValue = "10") int size
			) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		model.put("result", ledgerEntryService.search(type, search, username));
		return "member/entries/list";
	}
	
	@GetMapping("create")
	String addNew(ModelMap model, @PathVariable BalanceType type) {
		return "member/entries/edit";
	}
	
	@GetMapping("edit")
	String edit(@RequestParam String id) {
		return "member/entries/edit";
	}
	
	@PostMapping("save")
	String save(@Validated @ModelAttribute("form") LedgerEntryForm entryForm,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "member/entries/edit";
		}
		return "redirect:/member/balance/20250301-001";
	}
	
	@PostMapping("item/add")
	String addItem(@ModelAttribute(name = "form") LedgerEntryForm entryForm) {
		return "member/entries/edit";
	}
	
	@PostMapping("item/remove")
	String removeItem(@ModelAttribute(name = "form") LedgerEntryForm entryForm) {
		return "member/entries/edit";
	}
	
	@ModelAttribute("form")
	LedgerEntryForm ledgerForm(@PathVariable BalanceType type, @PathVariable(required = false) String id) {
		
		if(StringUtils.hasLength(id)) {
			return ledgerEntryService.findForEdit(id);
		}
		return new LedgerEntryForm();
	}
	
	@ModelAttribute(name = "ledgers")
	List<LedgerSelectItem> getLedgers(@PathVariable BalanceType type) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return ledgerManagementService.findForEntry(username, type);
	}
}
