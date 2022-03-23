Brewer.Venda = (function(){
	
	function Venda(tabelaItens){
		this.tabelaItens = tabelaItens;
		this.valorTotalBox = $('.js-valor-total-box');
		this.valorFreteInput = $('#valorFrete');
		this.valorDescontoInput = $('#valorDesconto');
		this.boxValorTotal = $('.js-box-valor-total');
		
		this.valorTotalItens = 0;
		this.valorFrete = 0;
		this.valorDesconto = 0;
	}
	
	Venda.prototype.iniciar = function(){
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		this.valorFreteInput.on('keyup', onValorFreteAlterado.bind(this));
		this.valorDescontoInput.on('keyup', onValorDescontoAlterado.bind(this));
		
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterado.bind(this));
		this.valorFreteInput.on('keyup', onValoresAlterado.bind(this));
		this.valorDescontoInput.on('keyup', onValoresAlterado.bind(this));
	}
	
	function onTabelaItensAtualizada(evento,valorTotalItens){
		this.valorTotalItens = valorTotalItens == null ? 0 : parseFloat(valorTotalItens);
	}
	
	function onValorFreteAlterado(evento){
		this.valorFrete = Brewer.recuperarValor($(evento.target).val());
	}
	
	function onValorDescontoAlterado(evento){
		this.valorDesconto = Brewer.recuperarValor($(evento.target).val());
	}
	
	function onValoresAlterado(){
		var valorTotal = this.valorTotalItens + this.valorFrete - this.valorDesconto;
		this.valorTotalBox.html(Brewer.formatarMoeda(valorTotal));
		
		this.boxValorTotal.removeClass('bw-tem-erro');
		if(valorTotal < 0){
			this.boxValorTotal.addClass('bw-tem-erro');
		}
	}
	
	return Venda;
}());

$(function(){
	
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new Brewer.TabelaItens(autocomplete);
	tabelaItens.iniciar();
	
	var venda = new Brewer.Venda(tabelaItens);
	venda.iniciar();
})