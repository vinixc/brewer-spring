var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function(){
	
	function MascaraCpfCnpj(){
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpfCnpj]');
		this.inputCpfCnpj = $('#cpfCnpj');
	}
	
	MascaraCpfCnpj.prototype.iniciar = function(){
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
	}
	
	function onTipoPessoaAlterado(event){
		var tipoPessoaSeleciona = $(event.currentTarget);
		var documento = tipoPessoaSeleciona.data('documento');
		this.labelCpfCnpj.text(documento);
		this.inputCpfCnpj.mask(tipoPessoaSeleciona.data('mascara'));
		this.inputCpfCnpj.val('');
		this.inputCpfCnpj.removeAttr('disabled');
	}
	
	return MascaraCpfCnpj;
	
}());


$(function() {
	
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
	
});