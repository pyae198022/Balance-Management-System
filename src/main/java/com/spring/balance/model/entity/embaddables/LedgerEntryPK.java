package com.spring.balance.model.entity.embaddables;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LedgerEntryPK {

	private long memberId;
	private LocalDate issueDate;
	private int seqNumber;
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	
	public String getCode() {
		
		return "%s%03D".formatted(issueDate.format(DATE_FORMATTER));
	}

	public static LedgerEntryPK parse(String id, Long memberId) {
		var pk = new LedgerEntryPK();
		
		var issueDate = LocalDate.parse(id.substring(0, 8), DATE_FORMATTER);
		var seqNumber = Integer.parseInt(id.substring(9));
		
		pk.setMemberId(memberId);
		pk.setIssueDate(issueDate);
		pk.setSeqNumber(seqNumber);
		
		return pk;
	}
}
