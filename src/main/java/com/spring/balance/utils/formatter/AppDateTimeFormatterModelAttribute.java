package com.spring.balance.utils.formatter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AppDateTimeFormatterModelAttribute {

	@ModelAttribute(name = "dateTime")
	public AppDateTimeFormatter dateTimeFormatter() {
		
		return new  AppDateTimeFormatter();
	}
}
