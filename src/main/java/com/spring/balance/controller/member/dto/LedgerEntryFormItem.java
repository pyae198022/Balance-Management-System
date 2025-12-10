package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;

import com.spring.balance.model.entity.LedgerEntryItem;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LedgerEntryFormItem {

	@NotBlank(message = "Please enter item name")
	private String itemName;
	@NotBlank(message = "Please enter unit price")
	private BigDecimal unitPrice;
	@NotBlank(message = "Please enter quantity")
	private Integer quantity;
	
	public static LedgerEntryFormItem from(LedgerEntryItem entity) {
		var item = new LedgerEntryFormItem();
		
		item.setItemName(entity.getItem());
		item.setUnitPrice(entity.getUnitPrice());
		item.setQuantity(entity.getQuantity());
		
		
		return item;
	}
}
