package com.spring.balance.utils.listener;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spring.balance.model.entity.consts.Role;
import com.spring.balance.model.repo.MemberActivityRepo;
import com.spring.balance.model.repo.service.AccessHistoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AunthenticationEventListener {

	private final AccessHistoryService service;
	private final MemberActivityRepo  activityRepo;
	
	@Transactional
	@EventListener
	public void handle(AuthenticationSuccessEvent event) {
		// update access history
		service.loginSuccess(event.getAuthentication().getName());
		
		// update last access history
		var authories = event.getAuthentication().getAuthorities();
		if(authories.stream().map(a -> a.getAuthority()).filter(a -> Role.Member.name().equals(a)).count() > 0) {
			
			var username = event.getAuthentication().getName();
			
			activityRepo.updateLastAccess(LocalDateTime.now(), username);
		};
	}
	
	@EventListener
	public void handle(AbstractAuthenticationFailureEvent event) {
		var message = switch(event) {
		case AuthenticationFailureBadCredentialsEvent e -> "Missing Login ID or Password.";
		case AuthenticationFailureDisabledEvent e -> "Account is disabled.";
		default -> "Other Authentication failure.";
		};
		
		service.loginError(event.getAuthentication().getName(), message);
	}
	
	@EventListener
	public void handle(LogoutSuccessEvent event) {
		service.logoutSuccess(event.getAuthentication().getName());
	}
	
	
}
