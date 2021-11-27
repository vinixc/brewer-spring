package br.com.vini.brewer.controller;

import org.springframework.stereotype.Controller;
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
	public String cadastrar(Cerveja cerveja) {
		System.out.println("SKU " + cerveja.getSku());
		
		return "cerveja/cadastroCerveja";
		
	}

}
