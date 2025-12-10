package com.spring.balance.model.repo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.balance.controller.anonymous.dto.SignUpForm;
import com.spring.balance.model.entity.Account;
import com.spring.balance.model.entity.Member;
import com.spring.balance.model.entity.MemberActivity;
import com.spring.balance.model.entity.consts.MemberStatus;
import com.spring.balance.model.entity.consts.Role;
import com.spring.balance.model.repo.AccountRepo;
import com.spring.balance.model.repo.MemberActivityRepo;
import com.spring.balance.model.repo.MemberRepo;
import com.spring.balance.utils.exception.AppBussinessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {

	private final AccountRepo accountRepo;
	private final MemberRepo memberRepo;
	private final PasswordEncoder passwordEncoder;
	private final MemberActivityRepo activityRepo;
	private final AccessHistoryService accessHistoryService;
	
	@Transactional
	public String signup(SignUpForm form) {
		
		if(accountRepo.existsById(form.getUsername())) {
			throw new AppBussinessException("Your email is already used.Please try another email.");
		}
		
		var account = new Account();
		account.setUsername(form.getUsername());
		account.setActive(true);
		account.setPassword(passwordEncoder.encode(form.getPassword()));
		account.setRole(Role.Member);
		
		accountRepo.save(account);
		
		var member = new Member();
		member.setAccount(account);
		member.setEmail(form.getUsername());
		member.setName(form.getName());
		
		memberRepo.save(member);
		
		var activity = new MemberActivity();
		activity.setMember(member);
		activity.setBalance(BigDecimal.ZERO);
		activity.setRegisteredAt(LocalDateTime.now());
		activity.setMemberStatus(MemberStatus.Active);
		
		activityRepo.save(activity);
		
		
		accessHistoryService.signUp(form.getUsername());
		
		return "Your account has been created successfully.Please sign up again.";
	}

}
