package br.com.vini.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public @ResponseBody String adicionarItem(Long idCerveja) {
		Cerveja cerveja = cervejaRepository.findOne(idCerveja);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		
		System.out.println(">>> Total de itens:" + tabelaItensVenda.getTotal());
		return "Item adicionado";
	}

}
