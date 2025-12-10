package com.spring.balance.controller.member.dto;

import com.spring.balance.model.entity.Ledger;
import com.spring.balance.model.entity.consts.BalanceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerForm {

	private Integer id;
	
	@NotNull(message = "Please select balance type")
	private BalanceType type;
	@NotBlank(message = "Please enter ledger name")
	private String name;
	private Boolean status;
	
	public static LedgerForm from(Ledger entity) {
		return new LedgerForm(
				entity.getId(),
				entity.getBalanceType(),
				entity.getName(),
				entity.isDeleted());
	}
}
