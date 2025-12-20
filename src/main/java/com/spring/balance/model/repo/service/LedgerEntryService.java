package com.spring.balance.model.repo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.spring.balance.controller.member.dto.LedgerEntryForm;
import com.spring.balance.controller.member.dto.LedgerEntryListItem;
import com.spring.balance.controller.member.dto.LedgerEntrySearch;
import com.spring.balance.model.PageResult;
import com.spring.balance.model.entity.LedgerEntry;
import com.spring.balance.model.entity.LedgerEntryItem;
import com.spring.balance.model.entity.LedgerEntry_;
import com.spring.balance.model.entity.Member;
import com.spring.balance.model.entity.consts.BalanceType;
import com.spring.balance.model.entity.embaddables.LedgerEntryItemPK;
import com.spring.balance.model.entity.embaddables.LedgerEntryPK;
import com.spring.balance.model.repo.LedgerEntryItemRepo;
import com.spring.balance.model.repo.LedgerEntryRepo;
import com.spring.balance.model.repo.LedgerRepo;
import com.spring.balance.model.repo.MemberRepo;
import com.spring.balance.utils.EntityOperation;
import com.spring.balance.utils.exception.AppBussinessException;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerEntryService {
	
	private final MemberRepo memberRepo;
	private final LedgerEntryRepo ledgerEntryRepo;
	private final LedgerEntryGenerator ledgerEntryGenerator;
	private final LedgerEntryItemRepo itemRepo;
	private final LedgerRepo ledgerRepo;
	
	public PageResult<LedgerEntryListItem> search(
			BalanceType type, LedgerEntrySearch search, String username, int page, int size) {
		
	
		return ledgerEntryRepo.search(queryFunc(username, type, search), countFunc(username, type, search), page, size);
	}

	private Function<CriteriaBuilder, CriteriaQuery<LedgerEntryListItem>> queryFunc(String username, BalanceType type, LedgerEntrySearch search) {
		// TODO Auto-generated method stub
		return cb -> {
			var cq = cb.createQuery(LedgerEntryListItem.class);
			var root = cq.from(LedgerEntry.class);
			
			LedgerEntryListItem.select(cq, cb, root);
			cq.where(search.where(cb, root,username, type));
			
			cq.orderBy(cb.desc(root.get(LedgerEntry_.ISSUE_AT)));
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>>  countFunc(String username, BalanceType type, LedgerEntrySearch search) {
				return cb -> {
					var cq = cb.createQuery(Long.class);
					var root = cq.from(LedgerEntry.class);
					
					cq.select(cb.count(root.get(LedgerEntry_.ID)));
					cq.where(search.where(cb, root,username, type));
					
					return cq;
				};
	}

	public LedgerEntryForm findForEdit(String id) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var member = memberRepo.findOneByAccountUsername(username).get();
		
		var entryPK = LedgerEntryPK.parse(id, member.getId());
		
		return EntityOperation.safeCall(ledgerEntryRepo.findById(entryPK)
				.map(LedgerEntryForm::from),
				 "ledger entry", "entry id", id);
	}

	@Transactional
	public String save(LedgerEntryForm entryForm) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var member =  memberRepo.findOneByAccountUsername(username).get();
		
		
		if(StringUtils.hasLength(entryForm.getId())) {
			
			return update(member, entryForm);
		}
		
		return insert(member, entryForm);
	}

	private String insert(Member member, LedgerEntryForm entryForm) {
		
		var entry = new LedgerEntry();
		entry.setId(ledgerEntryGenerator.next(member.getId(), LocalDate.now()));
		
		entry.setParticular(entryForm.getParticular());
		entry.setLedger(ledgerRepo.findById(entryForm.getLedgerId()).get());
		entry.setIssueAt(LocalDateTime.now());	
		
		var lastAmount = Optional.ofNullable(member.getActivity().getBalance()).orElse(BigDecimal.ZERO);
		var amount = entryForm.getItems().stream()
				.filter(a -> !a.isDeleted())
				.map(a -> a.getUnitPrice().multiply(BigDecimal.valueOf(a.getQuantity())))
				.reduce((a, b) -> a.add(b)).orElse(BigDecimal.ZERO);
		
		entry.setLastAmount(lastAmount);
		entry.setAmount(amount);
		
		entry = ledgerEntryRepo.save(entry);
		
		for(var i = 0;i < entryForm.getItems().size(); i++) {
			
			var item = entryForm.getItems().get(i);
			
			if(!item.isDeleted() ) {
				var entryItem = new LedgerEntryItem();
				var pk = new LedgerEntryItemPK();
				pk.setIssueDate(entry.getId().getIssueDate());
				pk.setMemberId(entry.getId().getMemberId());
				pk.setSeqNumber(entry.getId().getSeqNumber());
				pk.setItemNumber(i + 1);
				
				entryItem.setId(pk);
				
				entryItem.setEntry(entry);
				entryItem.setItem(item.getItemName());
				entryItem.setQuantity(item.getQuantity());
				entryItem.setUnitPrice(item.getUnitPrice());
			
				itemRepo.save(entryItem);
			}
		}
		
		// update Member last balance
		var balance = switch(entry.getLedger().getBalanceType()) {
		case Expenses -> entry.getLastAmount().subtract(amount);
		case Incomes -> entry.getLastAmount().add(amount);
		};
		
		member.getActivity().setBalance(balance);
		
		return entry.getId().getCode();
	}

	private String update(Member member, LedgerEntryForm entryForm) {
		
		// check issue date
		var entryId = LedgerEntryPK.parse(entryForm.getId(), member.getId());
		if(!entryId.getIssueDate().equals(LocalDate.now()))		 {
			throw new AppBussinessException("You can only update old entries.");
		}
		
		// Get Ledger Entry
		var entry = ledgerEntryRepo.findById(entryId).get();
		
		// Get Ledger entry items
		for (var item : entry.getItems()) {
			// Delete All items
			itemRepo.deleteById(item.getId());
		}
		
		// Insert all items
		for (var i = 0; i < entryForm.getItems().size(); i++) {

			var item = entryForm.getItems().get(i);

			if (!item.isDeleted()) {
				var entryItem = new LedgerEntryItem();
				var pk = new LedgerEntryItemPK();
				pk.setIssueDate(entry.getId().getIssueDate());
				pk.setMemberId(entry.getId().getMemberId());
				pk.setSeqNumber(entry.getId().getSeqNumber());
				pk.setItemNumber(i + 1);

				entryItem.setId(pk);

				entryItem.setEntry(entry);
				entryItem.setItem(item.getItemName());
				entryItem.setQuantity(item.getQuantity());
				entryItem.setUnitPrice(item.getUnitPrice());

				itemRepo.save(entryItem);
			}
		}
		
		// Update Ledger Entry info
		entry.setParticular(entryForm.getParticular());
		entry.setLedger(ledgerRepo.findById(entryForm.getLedgerId()).get());
		entry.setIssueAt(LocalDateTime.now());	
		
		var lastAmount = Optional.ofNullable(member.getActivity().getBalance()).orElse(BigDecimal.ZERO);
		var amount = entryForm.getItems().stream()
				.filter(a -> !a.isDeleted())
				.map(a -> a.getUnitPrice().multiply(BigDecimal.valueOf(a.getQuantity())))
				.reduce((a, b) -> a.add(b)).orElse(BigDecimal.ZERO);
		

		entry.setLastAmount(lastAmount);
		entry.setAmount(amount);
		
		// Update Member Balance
		var balance = switch(entry.getLedger().getBalanceType()) {
		case Expenses -> entry.getLastAmount().subtract(amount);
		case Incomes -> entry.getLastAmount().add(amount);
		};
		
		member.getActivity().setBalance(balance);
		
		// Update remaining entry balance
		var entries = ledgerEntryRepo.findRemainingEntries(member.getId(), entry.getId().getIssueDate(), entry.getId().getSeqNumber());
		
		for(var remain : entries) {
			remain.setLastAmount(member.getActivity().getBalance());
			
			var remainAmount = remain.getItems().stream()
					.map(a -> a.getUnitPrice().multiply(BigDecimal.valueOf(a.getQuantity())))
					.reduce((a, b) -> a.add(b)).orElse(BigDecimal.ZERO);
			
			var remainBalance = switch(remain.getLedger().getBalanceType()) {
			case Expenses -> remain.getLastAmount().subtract(remainAmount);
			case Incomes -> remain.getLastAmount().add(remainAmount);
			};
			
			member.getActivity().setBalance(remainBalance);
		}
		
		return entry.getId().getCode();
	}
}
