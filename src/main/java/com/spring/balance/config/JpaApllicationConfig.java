package com.spring.balance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.spring.balance.model.BaseRepoImpl;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.spring.balance.model",
        repositoryBaseClass = BaseRepoImpl.class
        
)
@EnableJpaAuditing
public class JpaApllicationConfig {

	
}
