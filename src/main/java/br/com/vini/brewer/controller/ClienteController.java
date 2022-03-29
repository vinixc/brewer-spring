package br.com.vini.brewer.controller;

import java.util.List;

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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.controller.page.PageWrapper;
import br.com.vini.brewer.exception.CpfCnpjClienteJaCadastradoException;
import br.com.vini.brewer.model.Cliente;
import br.com.vini.brewer.model.TipoPessoa;
import br.com.vini.brewer.repository.ClienteRepository;
import br.com.vini.brewer.repository.EstadoRepository;
import br.com.vini.brewer.repository.filter.ClienteFilter;
import br.com.vini.brewer.service.CadastroClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/cadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estadoRepository.findAll());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(cliente);
		}
		
		try {
			cadastroClienteService.salvar(cliente);
		}catch (CpfCnpjClienteJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		
		attributes.addFlashAttribute("mensagem","Cliente salvo com sucesso!");
		
		return new ModelAndView("redirect:/cliente/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter, BindingResult bindingResult, 
			@PageableDefault(size = 5) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cliente/pesquisaCliente");
		
		Page<Cliente> page = clienteRepository.filtrar(clienteFilter, pageable);
		PageWrapper<Cliente> pageWrapper = new PageWrapper<>(page, httpServletRequest);
		
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}
	
	@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Cliente> pesquisar(String nome){
		validarTamanhoNome(nome);
		return this.clienteRepository.findByNomeStartingWithIgnoreCase(nome);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long id) {
		Cliente cliente = this.clienteRepository.carregarComCidade(id);
		ModelAndView mv = novo(cliente);
		mv.addObject(cliente);
		
		return mv;
	}
	

	private void validarTamanhoNome(String nome) {
		if(StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}
	
	
	/**
	 * Tratando a IllegalArgumentException que esse controller lança, 
	 * se outro controller lancar a mesma exception, NAO entra nesse metodo!!
	 * 
	 * @param IllegalArgumentException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e){
		return ResponseEntity.badRequest().build();
	}
}
