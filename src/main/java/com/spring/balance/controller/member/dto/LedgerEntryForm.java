package com.spring.balance.controller.member.dto;

import java.util.ArrayList;
import java.util.List;

import com.spring.balance.model.entity.LedgerEntry;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LedgerEntryForm {

	private String id;
	
	@NotBlank(message = "Please select ledger.")
	private Integer ledgerId;
	@NotBlank(message = "Please enter particular.")
	private String particular;
	
	@NotEmpty(message = "Please enter items.")
	private List<@Valid LedgerEntryFormItem> items = new ArrayList<LedgerEntryFormItem>();
	
	public static LedgerEntryForm from(LedgerEntry entity) {
		
		var form = new LedgerEntryForm();
		
		form.setId(entity.getId().getCode());
		form.setLedgerId(entity.getLedger().getId());
		form.setParticular(entity.getParticular());
		form.setItems(entity.getItems().stream().map(LedgerEntryFormItem::from).toList());;
		
		return form;
	}
	
}
