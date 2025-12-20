package com.spring.balance.model.entity.embaddables;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntrySeqPK {

	private long memberId;
	private LocalDate issueDate;
}
