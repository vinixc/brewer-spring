Brewer = Brewer || {};

Brewer.MultiSelecao = (function(){
	
	function MultiSelecao(){
		this.statusBtn = $('.js-status-btn');
		this.selecaoCheckbox = $('.js-selecao');
		this.selecaoTodosCheckbox = $('.js-selecao-todos');
	}
	
	MultiSelecao.prototype.iniciar = function(){
		this.statusBtn.on('click', onStatusBtnClicado.bind(this));
		this.selecaoTodosCheckbox.on('click', onSelecaoTodosClicado.bind(this));
		this.selecaoCheckbox.on('click', onSelecaoCheckboxClicado.bind(this));
	}
	
	function onStatusBtnClicado(event){
		
		var botaoClicado = $(event.currentTarget);
		var status = botaoClicado.data('status');
		var url = botaoClicado.data('url');
		
		var checkboxSelecionados = this.selecaoCheckbox.filter(':checked');
		
		var ids = $.map(checkboxSelecionados, function(c){
			return $(c).data('codigo');
		});
		
		if(ids.length > 0){
			
			$.ajax({
				url: url,
				method: 'PUT',
				data: {
					ids: ids,
					status: status
				},
				success: function(){
					window.location.reload();
				}
			});
			
		}
		
	}
	
	function onSelecaoTodosClicado(){
		var status = this.selecaoTodosCheckbox.prop('checked');
		this.selecaoCheckbox.prop('checked',status);
		statusBotaoAcao.call(this,status);
	}
	
	function onSelecaoCheckboxClicado(){
		var selecaoChecados = this.selecaoCheckbox.filter(':checked');
		this.selecaoTodosCheckbox.prop('checked', selecaoChecados.length >= this.selecaoCheckbox.length);
		
		statusBotaoAcao.call(this, selecaoChecados.length);
	}
	
	function statusBotaoAcao(ativar){
		ativar ? this.statusBtn.removeClass('disabled') : this.statusBtn.addClass('disabled');
	}
	
	return MultiSelecao;
}());

$(function() {
	var multiSelecao = new Brewer.MultiSelecao();
	multiSelecao.iniciar();
});