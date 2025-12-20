package com.spring.balance.model.repo.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.balance.model.entity.LedgerEntrySeq;
import com.spring.balance.model.entity.embaddables.LedgerEntryPK;
import com.spring.balance.model.entity.embaddables.LedgerEntrySeqPK;
import com.spring.balance.model.repo.LedgerEntrySeqRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LedgerEntryGenerator {
	
	private final LedgerEntrySeqRepo repo;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public LedgerEntryPK next(long memberId, LocalDate issueDate) {
		
		var seqId = new LedgerEntrySeqPK(memberId, issueDate);
		
		var entrySeq = repo.findById(seqId).orElseGet(() -> {
			var seq = new LedgerEntrySeq();
			seq.setId(seqId);
			return repo.save(seq);
		});
		
		return entrySeq.next();
	}
}
