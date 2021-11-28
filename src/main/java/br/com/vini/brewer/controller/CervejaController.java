package br.com.vini.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.model.Cerveja;

@Controller
public class CervejaController {
	
	@RequestMapping("/cerveja/novo")
	public String novo() {
		return "cerveja/cadastroCerveja";
	}
	
	@RequestMapping(value = "/cerveja/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			model.addAttribute("mensagem","Erro no formulario");
			return "cerveja/cadastroCerveja";
		}
		
		System.out.println("SKU " + cerveja.toString());
		attributes.addFlashAttribute("mensagem","Cerveja salva com sucesso!");
		
		return "redirect:/cerveja/novo";
	}
}
