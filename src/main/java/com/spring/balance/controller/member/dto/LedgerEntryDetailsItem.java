package com.spring.balance.controller.member.dto;

import java.math.BigDecimal;

import com.spring.balance.model.entity.LedgerEntryItem;

public record LedgerEntryDetailsItem(
		String itemName,
		BigDecimal unitPrice,
		int quantity
		) {

	public BigDecimal getTotal() {
		return unitPrice.multiply(BigDecimal.valueOf(quantity));
	}
	
	public static LedgerEntryDetailsItem from(LedgerEntryItem entry) {
		return new LedgerEntryDetailsItem(entry.getItem(), entry.getUnitPrice(), entry.getQuantity());
	}
}
