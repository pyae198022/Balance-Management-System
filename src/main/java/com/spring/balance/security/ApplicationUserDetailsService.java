package com.spring.balance.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.balance.model.repo.AccountRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService{
	
	@Autowired
	private AccountRepo accountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return accountRepo.findById(username)
				.map(account -> User.withUsername(username)
						.password(account.getPassword())
						.authorities(account.getRole().name())
						.disabled(!account.isActive())
						.build())
				.orElseThrow(() -> new UsernameNotFoundException("Invalid Login ID"));
	}

}
