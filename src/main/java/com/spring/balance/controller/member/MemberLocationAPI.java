package com.spring.balance.controller.member;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.balance.model.entity.District;
import com.spring.balance.model.entity.TownShip;
import com.spring.balance.model.repo.service.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("member/location")
public class MemberLocationAPI {
	
	private final LocationService service;

	@GetMapping("district")
	List<District> findDistrict(@RequestParam int regionId) {
		System.out.println(service.findDistrictByRegion(regionId));
		return service.findDistrictByRegion(regionId);
	}
	
	@GetMapping("township")
	List<TownShip> findTownship(@RequestParam int districtId) {
		return service.findTownshipByDistrict(districtId);
	}
	
	
}
