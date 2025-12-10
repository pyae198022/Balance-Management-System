package com.spring.balance.model.repo;

import java.util.List;

import com.spring.balance.model.BaseRepo;
import com.spring.balance.model.entity.TownShip;

public interface TownShipRepo extends BaseRepo<TownShip, Integer>{

	List<TownShip> findByDistrictId(int districtId);

}
