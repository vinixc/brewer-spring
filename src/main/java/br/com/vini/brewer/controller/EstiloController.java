package br.com.vini.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.model.Estilo;

@Controller
@RequestMapping("/estilo")
public class EstiloController {
	
	@RequestMapping("/novo")
	public String novo(Estilo estilo) {
		return "estilo/cadastroEstilo";
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String cadastro(@Valid Estilo estilo, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		
		attributes.addFlashAttribute("mensagem","Estilo salvo com sucesso!");
		
		return "redirect:/estilo/novo";
	}

}
