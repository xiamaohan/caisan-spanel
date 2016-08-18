package io.jee.caisan.spanel.controller.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

import io.jee.medusa.util.CalendarUtils;

@RestController
@RequestMapping(path="/api/captcha")
public class KaptchaController {

	private Properties props = new Properties();

	private Producer captchaProducer;

	public KaptchaController() {

//		this.props.put("kaptcha.border", "no");
//		this.props.put("kaptcha.textproducer.font.color", "black");
		this.props.put("kaptcha.image.width", "120");
//		this.props.put("kaptcha.image.height", "35");
		this.props.put("kaptcha.textproducer.char.length", "4");
//		this.props.put("kaptcha.textproducer.char.space", "4");
//		this.props.put("kaptcha.textproducer.font.size", "28");

		Config config = new Config(this.props);
		this.captchaProducer = config.getProducerImpl();
	}

	@GetMapping(path="show.jpg")
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set to expire far in the past.
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");

		// return a jpeg
		response.setContentType("image/jpeg");

		// create the text for the image
		String capText = captchaProducer.createText();

		// store the text in the session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_DATE, new Date());

		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);

		ServletOutputStream out = response.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}
	
	@GetMapping("verify")
    public Map<String, Object> verify(String captcha, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		Date captchaTime = (Date) session.getAttribute(Constants.KAPTCHA_SESSION_DATE);
		String sessionCaptcha = (String) session .getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isNotBlank(captcha)&&StringUtils.equalsIgnoreCase(sessionCaptcha, captcha)){
			if(CalendarUtils.addMinute(-10).compareTo(captchaTime)<0){
				result.put("valid", true);
			}else{
				result.put("valid", false);
				result.put("message", "验证码超时请刷新");
			}
		}else{
			result.put("valid", false);
			result.put("message", "验证码错误");
		}
		return result;
	}

	public static boolean validateCaptchaAndRemove(String captcha, HttpSession session) {
		Date captchaTime = (Date) session.getAttribute(Constants.KAPTCHA_SESSION_DATE);
		String sessionCaptcha = (String) session .getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(StringUtils.isBlank(captcha)||StringUtils.isBlank(sessionCaptcha)||captchaTime==null){
			return false;
		}
		if(StringUtils.equalsIgnoreCase(sessionCaptcha, captcha)&&CalendarUtils.addMinute(-10).compareTo(captchaTime)<0){
			session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
			session.removeAttribute(Constants.KAPTCHA_SESSION_DATE);
			return true;
		}else{
			return false;
		}

	}

}