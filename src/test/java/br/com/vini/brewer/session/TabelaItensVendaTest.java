package br.com.vini.brewer.session;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import br.com.vini.brewer.model.Cerveja;

 public class TabelaItensVendaTest {
	
	private TabelaItensVenda tabelaItensVenda;
	
	@Before
	public void setUp() {
		this.tabelaItensVenda = new TabelaItensVenda(UUID.randomUUID().toString());
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		 assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveCalcularOValorTotalComUmItem() throws Exception {
		Cerveja cerveja = new Cerveja();
		BigDecimal valor = new BigDecimal("8.90");
		cerveja.setValor(valor);
		
		tabelaItensVenda.adicionarItem(cerveja,1);
		
		assertEquals(valor, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {
		Cerveja c1 = new Cerveja();
		BigDecimal v1 = new BigDecimal("8.90");
		c1.setValor(v1);
		c1.setId(1l);
		
		Cerveja c2 = new Cerveja();
		BigDecimal v2 = new BigDecimal("4.99");
		c2.setValor(v2);
		c2.setId(2l);
		
		tabelaItensVenda.adicionarItem(c1,1);
		tabelaItensVenda.adicionarItem(c2,2);
		
		assertEquals(new BigDecimal("18.88"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmaCerveja() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setId(1l);
		c1.setValor(new BigDecimal("4.50"));
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.adicionarItem(c1, 1);
		
		assertEquals(1, tabelaItensVenda.getItens().size());
		assertEquals(new BigDecimal("9.00"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveAlterarQuantidadeDoItem() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setId(1l);
		c1.setValor(new BigDecimal("4.50"));
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.alterarQuantidadeItens(c1, 3);
		
		assertEquals(1, tabelaItensVenda.getItens().size());
		assertEquals(new BigDecimal("13.50"), tabelaItensVenda.getValorTotal());
		
	}
	
	@Test
	public void deveExcluirItem() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setValor(new BigDecimal("8.90"));
		c1.setId(1l);
		
		Cerveja c2 = new Cerveja();
		c2.setValor(new BigDecimal("4.99"));
		c2.setId(2l);
		
		Cerveja c3 = new Cerveja();
		c3.setValor(new BigDecimal("2.00"));
		c3.setId(3l);
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.adicionarItem(c2, 2);
		tabelaItensVenda.adicionarItem(c3, 1);
		
		tabelaItensVenda.excluirItem(c2);
		
		assertEquals(2, tabelaItensVenda.getItens().size());
		assertEquals(new BigDecimal("10.90"), tabelaItensVenda.getValorTotal());
	}

}
