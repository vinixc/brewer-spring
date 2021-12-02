package br.com.vini.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.model.Cidade;

@Controller
@RequestMapping("/cidade")
public class CidadeController {
	
	@RequestMapping(value = "/nova")
	public String novo(Cidade cidade) {
		return "cidade/cadastroCidade";
	}
	
	@RequestMapping(value = "/nova", method = RequestMethod.POST)
	public String cadastrar(@Valid Cidade cidade, BindingResult result, Model model, RedirectAttributes attributes) {
		
		attributes.addFlashAttribute("mensagem","Cidade salva com sucesso!");
		
		return "redirect:/cidade/nova";
	}

}
