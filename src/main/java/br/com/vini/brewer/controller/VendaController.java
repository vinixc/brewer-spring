package br.com.vini.brewer.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.controller.page.PageWrapper;
import br.com.vini.brewer.controller.validator.VendaValidator;
import br.com.vini.brewer.dto.VendaMes;
import br.com.vini.brewer.dto.VendaOrigem;
import br.com.vini.brewer.exception.QuantidadeInsuficienteException;
import br.com.vini.brewer.mail.Mailer;
import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.model.ItemVenda;
import br.com.vini.brewer.model.StatusVenda;
import br.com.vini.brewer.model.Venda;
import br.com.vini.brewer.repository.CervejaRepository;
import br.com.vini.brewer.repository.VendaRepository;
import br.com.vini.brewer.repository.filter.VendaFilter;
import br.com.vini.brewer.security.UsuarioSistema;
import br.com.vini.brewer.service.CadastroVendaService;
import br.com.vini.brewer.session.TabelasItensSession;

@Controller
@RequestMapping("/venda")
public class VendaController {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private TabelasItensSession tabelaItens;
	
	@Autowired
	private CadastroVendaService cadastroVendaService;
	
	@Autowired
	private VendaValidator vendaValidator;
	
	@Autowired
	private Mailer mailer;
	
	@InitBinder("venda")
	public void inicializarValidador(WebDataBinder binder) {
		binder.setValidator(vendaValidator);
	}
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/cadastroVenda");
		
		setUuid(venda);
		
		mv.addObject("itens", venda.getItens());
		mv.addObject("valorFrete", venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens", tabelaItens.getValorTotal(venda.getUuid()));
		return mv;
	}
	
	@PostMapping(value = "/nova", params = "salvar")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		validarVenda(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		this.cadastroVendaService.salvar(venda);
		
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso!");
		
		return new ModelAndView("redirect:/venda/nova");
	}

	@PostMapping(value = "/nova", params = "emitir")
	public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		validarVenda(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		try {
			this.cadastroVendaService.emitir(venda);
		}catch (QuantidadeInsuficienteException e) {
			
		}
		
		attributes.addFlashAttribute("mensagem", "Venda emitida com sucesso!");
		
		return new ModelAndView("redirect:/venda/nova");
	}
	
	@PostMapping(value = "/nova", params = "enviarEmail")
	public ModelAndView enviarEmail(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		validarVenda(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		this.cadastroVendaService.salvar(venda);
		mailer.enviar(venda);
		
		attributes.addFlashAttribute("mensagem", String.format("Venda n° %d salva com sucesso e e-mail enviado", venda.getId()));
		
		return new ModelAndView("redirect:/venda/nova");
	}
	
	@PostMapping(value = "/nova", params = "cancelar")
	public ModelAndView cancelar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		try {
			this.cadastroVendaService.cancelar(venda);
		}catch (AccessDeniedException e) {
			return new ModelAndView("/403");
		}
		
		attributes.addFlashAttribute("mensagem", String.format("Venda n° %d cancelada com sucesso", venda.getId()));
		return new ModelAndView("redirect:/venda/" + venda.getId());
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long idCerveja, String uuid) {
		
		Cerveja cerveja = cervejaRepository.findOne(idCerveja);
		tabelaItens.adicionarItem(uuid,cerveja, 1);
		return mvTabelaItensVenda(uuid);
	}
	
	@PutMapping("/item/{idCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("idCerveja") Cerveja cerveja, Integer quantidade,String uuid) {
		tabelaItens.alterarQuantidadeItens(uuid,cerveja, quantidade);
		return mvTabelaItensVenda(uuid);
	}
	
	@DeleteMapping("/item/{uuid}/{idCerveja}")
	public ModelAndView excluirItem(@PathVariable("idCerveja") Cerveja cerveja, @PathVariable String uuid) {
		tabelaItens.excluirItem(uuid,cerveja);
		return mvTabelaItensVenda(uuid);
	}
	
	@GetMapping
	public ModelAndView pesquisar(VendaFilter vendaFilter, 
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("venda/pesquisaVenda");
		mv.addObject("statusVenda", StatusVenda.values());
		
		Page<Venda> page = vendaRepository.filtrar(vendaFilter, pageable);
		PageWrapper<Venda> wrapper = new PageWrapper<>(page, request);
		
		mv.addObject("pagina", wrapper);
		
		return mv;
		
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Venda venda = vendaRepository.buscarComItens(codigo);

		setUuid(venda);
		for(ItemVenda item : venda.getItens()) {
			tabelaItens.adicionarItem(venda.getUuid(), item.getCerveja(), item.getQuantidade());
		}
		
		ModelAndView mv = nova(venda);
		mv.addObject(venda);
		
		return mv;
	}
	
	@GetMapping("/totalPorMes")
	public @ResponseBody List<VendaMes> listarTotalVendaPorMes(){
		return vendaRepository.totalPorMes();
	}
	
	@GetMapping("/totalPorOrigem")
	public @ResponseBody List<VendaOrigem> listarTotalVendaPorOrigem(){
		return vendaRepository.totalPorOrigem();
	}

	private void setUuid(Venda venda) {
		if(StringUtils.isEmpty(venda.getUuid())) {
			venda.setUuid(UUID.randomUUID().toString());
		}
	}

	private ModelAndView mvTabelaItensVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/tabelaItensVenda");
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("uuid", uuid);
		mv.addObject("valorTotal",tabelaItens.getValorTotal(uuid));
		return mv;
	}
	
	private void validarVenda(Venda venda, BindingResult result) {
		venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
		venda.calcularValorTotal();

		vendaValidator.validate(venda, result);
	}

}
