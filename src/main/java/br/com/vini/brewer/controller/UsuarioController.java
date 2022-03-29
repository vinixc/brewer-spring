package br.com.vini.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.controller.page.PageWrapper;
import br.com.vini.brewer.exception.EmailJaCadastradoException;
import br.com.vini.brewer.exception.ImpossivelExcluirEntidadeException;
import br.com.vini.brewer.model.Usuario;
import br.com.vini.brewer.repository.GrupoRepository;
import br.com.vini.brewer.repository.UsuarioRepository;
import br.com.vini.brewer.repository.filter.UsuarioFilter;
import br.com.vini.brewer.service.CadastroUsuarioService;
import br.com.vini.brewer.service.StatusUsuario;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/cadastroUsuario");
		mv.addObject("grupos", grupoRepository.findAll());
		
		return mv;
	}
	
	@RequestMapping(value = {"/novo", "{\\+d}"}, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			this.cadastroUsuarioService.cadastrar(usuario);
		}catch (EmailJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		catch (Exception e) {
			e.printStackTrace();
			return novo(usuario);
		}
		
		attributes.addFlashAttribute("mensagem","Usuario salvo com sucesso!");
		
		return new ModelAndView("redirect:/usuario/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult bindingResult,
			@PageableDefault(size = 5) Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("usuario/pesquisaUsuario");
		
		mv.addObject("grupos", grupoRepository.findAll());
		
		Page<Usuario> page = usuarioRepository.filtrar(usuarioFilter,pageable);
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(page,request);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("ids[]") Long[] ids, @RequestParam("status") StatusUsuario status) {
		this.cadastroUsuarioService.alterarStatus(ids,status);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Usuario usuario = usuarioRepository.buscarComGrupos(codigo);
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long id){
		
		try {
			this.cadastroUsuarioService.excluir(id);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
	
}
