package com.spring.balance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spring.balance.utils.BalanceTypeConverter;

@Configuration
public class WebApplicationConfig implements WebMvcConfigurer{

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new BalanceTypeConverter());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/signin");
		registry.addViewController("/signin").setViewName("anonymous/signin");
	}
}
