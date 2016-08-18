package io.jee.caisan.spanel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path="/login")
public class LoginController {
	
	@GetMapping
	public ModelAndView login(){
		return new ModelAndView("login");
	}

}
