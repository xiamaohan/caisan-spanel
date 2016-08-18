package io.jee.caisan.spanel.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jee.caisan.spanel.util.ValidationUtils;
import io.jee.medusa.util.ResultModel;

@RestController
@RequestMapping("/api/valid")
public class ValidationController {
	
	@GetMapping(path="username")
	public Map<String, Object> username(String username){
		Map<String, Object> result = new HashMap<>();
		ResultModel<?> resultModel = ValidationUtils.validUsername(username);
		result.put("valid", resultModel.isSuccess());
		result.put("message", resultModel.getMessage());
		return result;
	}

}
