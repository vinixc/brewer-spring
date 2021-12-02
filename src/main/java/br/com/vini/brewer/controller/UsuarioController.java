package br.com.vini.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.model.Usuario;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@RequestMapping("/novo")
	public String novo(Usuario usuario) {
		return "usuario/cadastroUsuario";
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {
		
		attributes.addFlashAttribute("mensagem","Usuario salvo com sucesso!");
		
		return "redirect:/usuario/novo";
	}
}
