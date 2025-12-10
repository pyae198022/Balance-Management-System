package com.spring.balance.controller.anonymous;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.balance.controller.anonymous.dto.SignUpForm;
import com.spring.balance.model.repo.service.SignUpService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("signup")
@RequiredArgsConstructor
public class SignUpController {

	private final SignUpService signUpService;
	
	@GetMapping
	String index() {
		return "anonymous/signup";
	}
	
	@PostMapping
	String signUp(
			@Validated  @ModelAttribute SignUpForm form,
			BindingResult result,
			ModelMap model,
			RedirectAttributes redirectAttribute
			) {
		
		if(result.hasErrors()) {
			return "anonymous/signup";
		}
		
		try {
			
			// Sign Up Business
			var message = signUpService.signup(form);
			redirectAttribute.addFlashAttribute("message", message);
			
			return "redirect:/signin";
			
		}catch (Exception e) {
			model.put("message", e.getMessage());
			return "anonymous/signup";
		}
	}
	
	@ModelAttribute("form")
	SignUpForm signUpForm() {
		return new SignUpForm();
	}
}


