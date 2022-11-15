package br.com.vini.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vini.brewer.repository.VendaRepository;

@Controller
public class DashboardController {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("dashboard");
		mv.addObject("vendasNoAno", vendaRepository.valorTotalNoAno());
		mv.addObject("vendasNoMes", vendaRepository.valorTotalNoMes());
		mv.addObject("tickerMedio", vendaRepository.valorTickerMedioNoAno());
		
		return mv;
	}
}
