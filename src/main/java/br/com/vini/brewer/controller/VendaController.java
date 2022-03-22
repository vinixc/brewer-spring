package br.com.vini.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.repository.CervejaRepository;
import br.com.vini.brewer.session.TabelaItensVenda;

@Controller
@RequestMapping("/venda")
public class VendaController {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private TabelaItensVenda tabelaItensVenda;
	
	@GetMapping("/nova")
	public String nova() {
		return "venda/cadastroVenda";
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long idCerveja) {
		
		Cerveja cerveja = cervejaRepository.findOne(idCerveja);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		
		ModelAndView mv = new ModelAndView("venda/tabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		
		return mv;
	}
	
	@PutMapping("/item/{idCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long idCerveja, Integer quantidade) {
		Cerveja cerveja = cervejaRepository.findOne(idCerveja);
		tabelaItensVenda.alterarQuantidadeItens(cerveja, quantidade);
		
		ModelAndView mv = new ModelAndView("venda/tabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		
		return mv;
	}

}
