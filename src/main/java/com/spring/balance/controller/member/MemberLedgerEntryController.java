package com.spring.balance.controller.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.balance.controller.member.dto.LedgerEntryForm;
import com.spring.balance.controller.member.dto.LedgerEntryFormItem;
import com.spring.balance.controller.member.dto.LedgerEntrySearch;
import com.spring.balance.controller.member.dto.LedgerSelectItem;
import com.spring.balance.model.entity.consts.BalanceType;
import com.spring.balance.model.repo.service.LedgerEntryService;
import com.spring.balance.model.repo.service.LedgerManagementService;
import com.spring.balance.utils.exception.AppBussinessException;

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
		
		model.put("result", ledgerEntryService.search(type, search, username, page, size));
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
	String save( ModelMap model,@Validated @ModelAttribute("form") LedgerEntryForm entryForm,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "member/entries/edit";
		}
		
		try {
			var id = ledgerEntryService.save(entryForm);
			return "redirect:/member/balance/%s".formatted(id);
			
		}catch (AppBussinessException e) {
			model.put("error", e.getMessage());
			return "member/entries/edit";
		}	
	}
	
	@PostMapping("item/add")
	String addItem(@ModelAttribute(name = "form") LedgerEntryForm entryForm) {
		
		entryForm.getItems().add(new LedgerEntryFormItem());
		return "member/entries/edit";
	}
	
	@PostMapping("item/remove")
	String removeItem(@ModelAttribute(name = "form") LedgerEntryForm entryForm) {
		
		return "member/entries/edit";
	}
	
	@ModelAttribute("form")
	LedgerEntryForm ledgerForm(@PathVariable BalanceType type, @RequestParam(required = false) String id) {
		
		var form = new LedgerEntryForm();
		
		if(StringUtils.hasLength(id)) {
			form =  ledgerEntryService.findForEdit(id);
		}
		
		if (form.getItems().isEmpty() || null == form.getItems()) {
			form.getItems().add(new LedgerEntryFormItem());
		}
		return form;
	}
	
	@ModelAttribute(name = "ledgers")
	List<LedgerSelectItem> getLedgers(@PathVariable BalanceType type) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return ledgerManagementService.findForEntry(username, type);
	}
	
	@ModelAttribute(name = "urlType")
	String getType(@PathVariable BalanceType type) {
		return type.name().toLowerCase();
	}
}
