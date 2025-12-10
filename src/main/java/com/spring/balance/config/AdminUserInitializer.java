package com.spring.balance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.balance.model.entity.Account;
import com.spring.balance.model.entity.consts.Role;
import com.spring.balance.model.repo.AccountRepo;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@PropertySource(value = "classpath:/admin-user.properties")
public class AdminUserInitializer {

	private final PasswordEncoder encoder;
	private final AccountRepo accountRepo;
	
	@Value("${app.admin.username}")
	private String username;
	@Value("${app.admin.password}")
	private String password;
	
	@PostConstruct
	@Transactional
	public void initialize() {
		if(accountRepo.count() == 0L) {
			var admin  = new Account();
			admin.setUsername(username);
			admin.setPassword(encoder.encode(password));
			admin.setRole(Role.Admin);
			admin.setActive(true);
			accountRepo.save(admin);
		}
	}
}
