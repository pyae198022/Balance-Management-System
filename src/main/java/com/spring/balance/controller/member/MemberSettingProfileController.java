package com.spring.balance.controller.member;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.balance.controller.member.dto.ProfileEditForm;
import com.spring.balance.model.entity.District;
import com.spring.balance.model.entity.Region;
import com.spring.balance.model.entity.TownShip;
import com.spring.balance.model.repo.service.LocationService;
import com.spring.balance.model.repo.service.MemberProfileService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("member/profile")
public class MemberSettingProfileController {

	private final LocationService locationService;
	private final MemberProfileService profileService;
	
	
	@GetMapping
	String editProfile() {
		return "member/profile/edit";
	}
	
	@PostMapping("photo")
	String uploadPhoto(@RequestParam MultipartFile file, HttpServletRequest request) {
		
		var username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		
		var imageFolder = request.getServletContext().getRealPath("/resources/image");
		profileService.save(username, imageFolder, file);
		
		return "redirect:/member/profile";
	}
	
	@PostMapping
	String updateProfile(@Validated @ModelAttribute ProfileEditForm form, BindingResult result) {
		
		if(result.hasErrors()) {
			return "member/profile/edit";
		}
		
		var username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		
		profileService.save(username, form);
		
		return "redirect:/member/home";
	}
	
	@ModelAttribute(name = "regions")
	List<Region> getRegions() {
		return locationService.getAllLocations();
	}
	
	@ModelAttribute(name = "districts")
	List<District> getDistricts(@ModelAttribute(name="form") ProfileEditForm editForm) {
		return locationService.getDistrictForSelectedTownship(editForm.getTownship());
	}
	
	@ModelAttribute(name = "townships")
	List<TownShip> getTownship(@ModelAttribute(name="form") ProfileEditForm editForm) {
		System.out.println(locationService.getDistrictForSelectedTownship(editForm.getTownship()));
		return locationService.getTownshipForSelectedTownship(editForm.getTownship());
	}
	
	@ModelAttribute(name = "form")
	ProfileEditForm editForm() {
		var username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		
		return profileService.getEditForm(username);
	}
}
