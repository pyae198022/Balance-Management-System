package com.spring.balance.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.spring.balance.model.entity.consts.Role;
import com.spring.balance.security.LoginSuccessHandler;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, PersistentTokenRepository tokenRepository) throws Exception {
		
		// Request Authorization
		http.authorizeHttpRequests(req -> {
			req.requestMatchers("/", "/signin", "/signup", "/resources/**").permitAll();
			req.requestMatchers("/admin/**").hasAnyAuthority(Role.Admin.name());
			req.requestMatchers("/member/**").hasAnyAuthority(Role.Member.name());
			req.anyRequest().authenticated();
		});
		// Login Form
		http.formLogin(login -> {
			login.loginPage("/signin");
			login.successHandler(new LoginSuccessHandler());
		});
		

		http.rememberMe(remember -> {
			remember.tokenRepository(tokenRepository);
		});
		
		// Logout
		http.logout(logout -> {
			logout.logoutUrl("/signout");
			logout.logoutSuccessUrl("/");
		});
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	PersistentTokenRepository tokenRepository(DataSource datasource) {
		
		var bean = new JdbcTokenRepositoryImpl();
		bean.setDataSource(datasource);
		// first time only
		//bean.setCreateTableOnStartup(true);
		return bean;
	}
	
}
