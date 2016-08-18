package io.jee.caisan.spanel.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path="/admin")
public class AdminHomeController {
	
	@GetMapping(path="index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("admin/index");
	}

}
