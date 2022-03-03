package br.com.vini.brewer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.model.Cidade;
import br.com.vini.brewer.repository.CidadeRepository;

@Controller
@RequestMapping("/cidade")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@RequestMapping(value = "/nova")
	public String novo(Cidade cidade) {
		return "cidade/cadastroCidade";
	}
	
	@RequestMapping(value = "/nova", method = RequestMethod.POST)
	public String cadastrar(@Valid Cidade cidade, BindingResult result, Model model, RedirectAttributes attributes) {
		
		attributes.addFlashAttribute("mensagem","Cidade salva com sucesso!");
		
		return "redirect:/cidade/nova";
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorIdEstado(@RequestParam(name = "estado", defaultValue = "-1") Long idEstado){
		return (List<Cidade>) cidadeRepository.findByEstadoId(idEstado);
	}
}
