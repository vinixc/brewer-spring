package br.com.vini.brewer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vini.brewer.controller.validator.VendaValidator;
import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.model.Venda;
import br.com.vini.brewer.repository.CervejaRepository;
import br.com.vini.brewer.security.UsuarioSistema;
import br.com.vini.brewer.service.CadastroVendaService;
import br.com.vini.brewer.session.TabelasItensSession;

@Controller
@RequestMapping("/venda")
public class VendaController {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private TabelasItensSession tabelaItens;
	
	@Autowired
	private CadastroVendaService cadastroVendaService;
	
	@Autowired
	private VendaValidator vendaValidator;
	
	@InitBinder
	public void inicializarValidador(WebDataBinder binder) {
		binder.setValidator(vendaValidator);
	}
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/cadastroVenda");
		
		if(StringUtils.isEmpty(venda.getUuid())) {
			venda.setUuid(UUID.randomUUID().toString());
		}
		
		mv.addObject("itens", venda.getItens());
		mv.addObject("valorFrete", venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens", tabelaItens.getValorTotal(venda.getUuid()));
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
		venda.calcularValorTotal();

		vendaValidator.validate(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		this.cadastroVendaService.salvar(venda);
		
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso!");
		
		return new ModelAndView("redirect:/venda/nova");
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

	private ModelAndView mvTabelaItensVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/tabelaItensVenda");
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("uuid", uuid);
		mv.addObject("valorTotal",tabelaItens.getValorTotal(uuid));
		return mv;
	}

}
