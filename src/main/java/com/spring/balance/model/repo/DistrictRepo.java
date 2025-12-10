package com.spring.balance.model.repo;

import java.util.List;

import com.spring.balance.model.BaseRepo;
import com.spring.balance.model.entity.District;

public interface DistrictRepo extends BaseRepo<District, Integer>{

	List<District> findByRegionId(int regionId);

}
