package br.com.vini.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/venda")
public class VendaController {
	
	@GetMapping("/nova")
	public String nova() {
		return "venda/cadastroVenda";
	}

}
