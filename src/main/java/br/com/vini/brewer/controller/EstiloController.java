package br.com.vini.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.controller.page.PageWrapper;
import br.com.vini.brewer.exception.ImpossivelExcluirEntidadeException;
import br.com.vini.brewer.exception.NomeEstiloJaCadastradoException;
import br.com.vini.brewer.model.Estilo;
import br.com.vini.brewer.repository.EstiloRepository;
import br.com.vini.brewer.repository.filter.EstiloFilter;
import br.com.vini.brewer.service.CadastroEstiloService;

@Controller
@RequestMapping("/estilo")
public class EstiloController {
	
	@Autowired
	private CadastroEstiloService cadastroEstiloService;
	
	@Autowired
	private EstiloRepository repository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		ModelAndView mv = new ModelAndView("estilo/cadastroEstilo");
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastro(@Valid Estilo estilo, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(estilo);
		}
		
		boolean alteracao = estilo.isAlteracao();
		
		try {
			cadastroEstiloService.salvar(estilo);
		}catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return novo(estilo);
		}
		
		attributes.addFlashAttribute("mensagem","Estilo salvo com sucesso!");
		
		return new ModelAndView(alteracao ? "redirect:/estilo/" + estilo.getId() : "redirect:/estilo/novo");
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		estilo = cadastroEstiloService.salvar(estilo);
		
		return ResponseEntity.ok(estilo);
	}
	
	@GetMapping
	public ModelAndView pesquisar(EstiloFilter estiloFilter, BindingResult bindingResult, 
			@PageableDefault(size = 5) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("estilo/pesquisaEstilo");
		
		Page<Estilo> page = repository.filtrar(estiloFilter, pageable);
		PageWrapper<Estilo> pageWrapper = new PageWrapper<>(page, httpServletRequest);
		
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long id) {
		Estilo estilo = repository.findOne(id);
		ModelAndView mv = novo(estilo);
		mv.addObject(estilo);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long id) {
		
		try {
			cadastroEstiloService.excluir(id);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} 
		
		return ResponseEntity.ok().build();
	}

}
