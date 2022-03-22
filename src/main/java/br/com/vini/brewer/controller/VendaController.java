package br.com.vini.brewer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.CervejaRepository;
import br.com.vini.brewer.session.TabelasItensSession;

@Controller
@RequestMapping("/venda")
public class VendaController {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private TabelasItensSession tabelaItens;
	
	@GetMapping("/nova")
	public ModelAndView nova() {
		ModelAndView mv = new ModelAndView("venda/cadastroVenda");
		mv.addObject("uuid", UUID.randomUUID().toString());
		return mv;
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
