package com.spring.balance.model.repo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.balance.controller.member.dto.ChartAmountVO;
import com.spring.balance.model.BaseRepo;
import com.spring.balance.model.entity.LedgerEntry;
import com.spring.balance.model.entity.consts.BalanceType;
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

	 @Query(value = """
	 		select sum(e.amount) from LedgerEntry e  
	 		where e.ledger.member.account.username = :username and e.ledger.balanceType = :type
	 		and e.id.issueDate >= :from
	 		and e.id.issueDate <= :to
	 		""")
	 BigDecimal getSummaryData(String username, BalanceType type, LocalDate from, LocalDate to);

	 @Query(value = """
	 		select new com.spring.balance.controller.member.dto.ChartAmountVO(e.ledger.name, sum(e.amount))
	 		from  LedgerEntry e where e.ledger.member.account.username = :username and e.ledger.balanceType = :type
	 		and e.id.issueDate >= :from
	 		and e.id.issueDate <= :to
	 		group by e.ledger.name
	 		""")
	 List<ChartAmountVO> getLedgerData(String username, BalanceType type,  LocalDate from, LocalDate to);

	 List<LedgerEntry> findByIdMemberId(long id);

}
