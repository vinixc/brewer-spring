package br.com.vini.brewer.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.model.Origem;
import br.com.vini.brewer.model.Sabor;
import br.com.vini.brewer.repository.CervejaRepository;
import br.com.vini.brewer.repository.EstiloRepository;
import br.com.vini.brewer.repository.filter.CervejaFilter;
import br.com.vini.brewer.service.CadastroCervejaService;

@Controller
@RequestMapping("/cerveja")
public class CervejaController {
	
	private static final Logger logger = LoggerFactory.getLogger(CervejaController.class);
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@Autowired
	private EstiloRepository estiloRepository;
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/cadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estiloRepository.findAll());
		mv.addObject("origens", Origem.values());
		
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(cerveja);
		}
		
		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem","Cerveja salva com sucesso!");
		
		return new ModelAndView("redirect:/cerveja/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult bindingResult, @PageableDefault(size = 2) Pageable pageable) {
		ModelAndView mv = new ModelAndView("cerveja/pesquisaCerveja");
		mv.addObject("estilos", estiloRepository.findAll());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		mv.addObject("cervejas", cervejaRepository.filtrar(cervejaFilter,pageable));
		return mv;
	}
}
