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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.exception.NomeCidadeJaCadastradaException;
import br.com.vini.brewer.model.Cidade;
import br.com.vini.brewer.repository.CidadeRepository;
import br.com.vini.brewer.repository.EstadoRepository;
import br.com.vini.brewer.service.CadastroCidadeService;

@Controller
@RequestMapping("/cidade")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@RequestMapping(value = "/nova")
	public ModelAndView novo(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidade/cadastroCidade");
		mv.addObject("estados", estadoRepository.findAll());
		
		return mv;
	}
	
	@RequestMapping(value = "/nova", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(cidade);
		}
		
		try {
			this.cadastroCidadeService.salvar(cidade);
		}catch (NomeCidadeJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(cidade);
		}
		catch (Exception e) {
			e.printStackTrace();
			return novo(cidade);
		}
		
		attributes.addFlashAttribute("mensagem","Cidade salva com sucesso!");
		
		return new ModelAndView("redirect:/cidade/nova");
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorIdEstado(@RequestParam(name = "estado", defaultValue = "-1") Long idEstado){
		return (List<Cidade>) cidadeRepository.findByEstadoId(idEstado);
	}
}
