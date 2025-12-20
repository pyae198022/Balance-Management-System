package com.spring.balance.model.entity.embaddables;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.spring.balance.model.entity.LedgerEntrySeq;

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
		
		return "%s%03d".formatted(issueDate.format(DATE_FORMATTER), seqNumber);
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

	public static LedgerEntryPK from(LedgerEntrySeq ledgerEntrySeq) {
		var pk = new LedgerEntryPK();
		pk.setMemberId(ledgerEntrySeq.getId().getMemberId());
		pk.setIssueDate(ledgerEntrySeq.getId().getIssueDate());
		pk.setSeqNumber(ledgerEntrySeq.getSeqNumber());
		return pk;
	}
}
