package io.jee.caisan.spanel.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.jee.caisan.spanel.domain.Member;
import io.jee.caisan.spanel.service.MemberService;

@RestController
public class HomeController {
	
	@Resource
	private MemberService memberService;
	@Resource
	private JavaMailSender javaMailSender;

	@RequestMapping("/")
	public ModelAndView home() throws IOException, MessagingException {
	  MimeMessage message = javaMailSender.createMimeMessage();
	  MimeMessageHelper helper = new MimeMessageHelper(message);
	  helper.setTo("1164066@qq.com");
	  helper.setText("Thank you for ordering!");
	  helper.setFrom("webmaster@jee.io");

	  //javaMailSender.send(message);
		Member member = new Member();
		member.setUsername("32432");
		member.setPassword("4234234324");
		memberService.addMember("32432","34333");
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("java.class.path"));
//			Runtime.getRuntime().exec("cmd /c restart.bat", null, new File(System.getProperty("user.dir")));
		return new ModelAndView("index");
	}
}