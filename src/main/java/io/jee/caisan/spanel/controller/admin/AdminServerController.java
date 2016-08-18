package io.jee.caisan.spanel.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/admin/server")
public class AdminServerController {
	
	@GetMapping(path="list")
	public void index(Model model){
		model.addAttribute("dfsd", "dfsd");
	}

}
