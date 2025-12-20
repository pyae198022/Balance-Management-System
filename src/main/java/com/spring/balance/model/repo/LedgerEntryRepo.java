package com.spring.balance.model.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.balance.model.BaseRepo;
import com.spring.balance.model.entity.LedgerEntry;
import com.spring.balance.model.entity.embaddables.LedgerEntryPK;

public interface LedgerEntryRepo extends BaseRepo<LedgerEntry, LedgerEntryPK>{

	 @Query("""
		        select e
		        from LedgerEntry e
		        where e.id.memberId = :memberId
		          and e.id.issueDate = :issueDate
		          and e.id.seqNumber > :seqNumber
		    """)
		    List<LedgerEntry> findRemainingEntries(
		            @Param("memberId") long memberId,
		            @Param("issueDate") LocalDate issueDate,
		            @Param("seqNumber") int seqNumber
		    );

}
