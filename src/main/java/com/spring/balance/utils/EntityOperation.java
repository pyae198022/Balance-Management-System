package com.spring.balance.utils;

import java.util.Optional;

import com.spring.balance.utils.exception.AppBussinessException;

public class EntityOperation {

	public static <T, ID> T safeCall(Optional<T> optional, String resourceName, String keyName,ID keyValue) {
		return optional.orElseThrow(() -> new AppBussinessException("There is no %s with %s : %s".formatted(resourceName, keyName, keyValue)));
	}
}
