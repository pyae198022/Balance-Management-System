package com.spring.balance.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.spring.balance.model.entity.consts.BalanceType;

public class BalanceTypeConverter implements Converter<String, BalanceType>{

	@Override
	public BalanceType convert(String value) {
		
		if(StringUtils.hasLength(value)) {
			return BalanceType.from(value);
		}
		
		return null;
	}


}
