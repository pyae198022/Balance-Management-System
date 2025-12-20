package com.spring.balance.model.entity;

import com.spring.balance.model.entity.embaddables.LedgerEntryPK;
import com.spring.balance.model.entity.embaddables.LedgerEntrySeqPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class LedgerEntrySeq {

	@EmbeddedId
	private LedgerEntrySeqPK id;
	
	@Column(nullable = false)
	private int seqNumber;

	public LedgerEntryPK next() {
		seqNumber += 1;
		return LedgerEntryPK.from(this);
	}
	
}
