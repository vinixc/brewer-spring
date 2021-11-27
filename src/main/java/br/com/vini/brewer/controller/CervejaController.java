package br.com.vini.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.vini.brewer.model.Cerveja;

@Controller
public class CervejaController {
	
	@RequestMapping("/cerveja/novo")
	public String novo() {
		return "cerveja/cadastroCerveja";
	}
	
	@RequestMapping(value = "/cerveja/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result) {
		if(result.hasErrors()) {
			System.out.println("TEM ERROR " + result);
		}
		
		System.out.println("SKU " + cerveja.getSku());
		
		return "cerveja/cadastroCerveja";
		
	}

}
