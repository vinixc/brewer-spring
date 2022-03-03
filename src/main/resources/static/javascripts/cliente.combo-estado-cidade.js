var Brewer = Brewer || {};

Brewer.ComboEstado = (function() {
	
	function ComboEstado(){
		this.combo = $('#estado');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
		
	}
	
	ComboEstado.prototype.iniciar = function (){
		this.combo.on('change', onEstadoAlterado.bind(this));
	}
	
	function onEstadoAlterado(){
		this.emitter.trigger('alterado', this.combo.val());
	}
	
	return ComboEstado;
}());

Brewer.ComboCidade = (function(){
	
	function ComboCidade(comboEstado){
		this.comboEstado = comboEstado;
		this.combo = $('#cidade');
		this.imgLoading = $('.js-img-loading');
		
	}
	
	ComboCidade.prototype.iniciar = function (){
		reset.call(this);
		this.comboEstado.on('alterado', onEstadoAlterado.bind(this));
		var idEstado = this.comboEstado.combo.val();
		inicializarCidades.call(this,idEstado);
	}
	
	function onEstadoAlterado(evento, idEstado){
		 
		$('#idCidadeSelecionada').val('');
		inicializarCidades.call(this,idEstado);
	}
	
	function inicializarCidades(idEstado){
		if(idEstado){
			
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: {'estado': idEstado},
				beforeSend : iniciarRequesicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			
			resposta.done(onBuscarCidadesFinalizado.bind(this));
			
		}else{
			reset.call(this);
		}
	}
	
	function onBuscarCidadesFinalizado(cidades){
		var options = [];
		cidades.forEach(function(cidade){
			options.push('<option value="' + cidade.id + '">' + cidade.nome + '</option>');
		});
		
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
		
		var idCidadeSelecionada = $('#idCidadeSelecionada').val();
		if(idCidadeSelecionada){
			this.combo.val(idCidadeSelecionada);
		}
	}
	
	function reset(){
		this.combo.html('<option value="">Selecione a cidade</option>');
		this.combo.val('');
		this.combo.attr('disabled','disabled');
	}
	
	function iniciarRequesicao(){
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao(){
		this.imgLoading.hide();
	}
	
	return ComboCidade;
	
}());

$(function(){
	
	var comboEstado = new Brewer.ComboEstado();
	comboEstado.iniciar();
	
	var comboCidade = new Brewer.ComboCidade(comboEstado);
	comboCidade.iniciar();
	
});