package com.spring.balance.model.repo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.balance.controller.management.AdminHomeController;
import com.spring.balance.model.entity.District;
import com.spring.balance.model.entity.Region;
import com.spring.balance.model.entity.TownShip;
import com.spring.balance.model.repo.AccountRepo;
import com.spring.balance.model.repo.DistrictRepo;
import com.spring.balance.model.repo.RegionRepo;
import com.spring.balance.model.repo.TownShipRepo;
import com.spring.balance.security.AuditorAwareBeans;
import com.spring.balance.utils.EntityOperation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
	
	private final RegionRepo regionRepo;
	private final DistrictRepo districtRepo;
	private final TownShipRepo townshipRepo;


	public List<Region> getAllLocations() {
		return regionRepo.findAll();
	}

	public List<District> findDistrictByRegion(int regionId) {
		return districtRepo.findByRegionId(regionId);
	}

	public List<TownShip> findTownshipByDistrict(int districtId) {
		System.out.println(townshipRepo.findByDistrictId(districtId));
		return townshipRepo.findByDistrictId(districtId);
	}

	public List<District> getDistrictForSelectedTownship(Integer townshipId) {
		
		if(null != townshipId) {
			var township = EntityOperation.safeCall(townshipRepo.findById(townshipId), "township", "id", townshipId);
			var regionId = township.getDistrict().getRegion().getId();
			return districtRepo.findByRegionId(regionId);
		}
		return null;
	}

	public List<TownShip> getTownshipForSelectedTownship(Integer townshipId) {
		if(null != townshipId) {
			var township = EntityOperation.safeCall(townshipRepo.findById(townshipId), "township", "id", townshipId);
			var districtId = township.getDistrict().getId();
			return townshipRepo.findByDistrictId(districtId);
		}
		return null;
	}

}
