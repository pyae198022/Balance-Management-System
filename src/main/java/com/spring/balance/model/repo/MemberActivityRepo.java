package com.spring.balance.model.repo;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.spring.balance.model.BaseRepo;
import com.spring.balance.model.entity.MemberActivity;

public interface MemberActivityRepo extends BaseRepo<MemberActivity, Long>{

	@Modifying
	@Query(value = "update MemberActivity a set a.lastAccessAt = :lastAccessAt where a.member.account.username = :username")
	int updateLastAccess(LocalDateTime lastAccessAt, String username);

	Long countByRegisteredAtIsGreaterThanEqual(LocalDateTime atStartOfDay);
}
