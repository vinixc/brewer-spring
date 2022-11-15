package br.com.vini.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView md = new ModelAndView("dashboard");
		
		return md;
	}
}
