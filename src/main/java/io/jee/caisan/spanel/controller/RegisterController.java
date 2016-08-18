package io.jee.caisan.spanel.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.jee.caisan.spanel.controller.api.KaptchaController;
import io.jee.caisan.spanel.service.MemberService;
import io.jee.medusa.util.ResultModel;

@RestController
@RequestMapping(path="register")
public class RegisterController {
	
	@Resource
	private MemberService memberService;

	@GetMapping
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("register");
	}
	
	@PostMapping
	public ResultModel<?> register(HttpSession session, String username, String password, String captcha){
		if(!KaptchaController.validateCaptchaAndRemove(captcha, session)){
			return ResultModel.resultError("captcha", "验证码错误或超时，请重试。");
		}
		return memberService.addMember(username, password);
	}

}
