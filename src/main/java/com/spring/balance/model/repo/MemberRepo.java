package com.spring.balance.model.repo;

import java.util.Optional;

import com.spring.balance.model.BaseRepo;
import com.spring.balance.model.entity.Member;

public interface MemberRepo extends BaseRepo<Member, Long>{

	Optional<Member> findOneByAccountUsername(String username);

}
