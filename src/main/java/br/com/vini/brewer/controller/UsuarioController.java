package br.com.vini.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.com.vini.brewer.controller.page.PageWrapper;
import br.com.vini.brewer.exception.EmailJaCadastradoException;
import br.com.vini.brewer.model.Usuario;
import br.com.vini.brewer.repository.GrupoRepository;
import br.com.vini.brewer.repository.UsuarioRepository;
import br.com.vini.brewer.repository.filter.UsuarioFilter;
import br.com.vini.brewer.service.CadastroUsuarioService;

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
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
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
			@PageableDefault(size = 2) Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("usuario/pesquisaUsuario");
		
		mv.addObject("grupos", grupoRepository.findAll());
		
		Page<Usuario> page = usuarioRepository.filtrar(usuarioFilter,pageable);
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(page,request);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
}
