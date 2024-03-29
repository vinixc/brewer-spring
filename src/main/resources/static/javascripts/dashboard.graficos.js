var Brewer = Brewer || {};

Brewer.GraficoVendaPorMes = (function(){
	
	function GraficoVendaPorMes(){
		this.ctx = $('#graficoVendasPorMes')[0].getContext('2d');
		this.ctxOrigem = $('#graficoVendasPorOrigem')[0].getContext('2d');
	}
	
	GraficoVendaPorMes.prototype.iniciar = function(){
		
		$.ajax({
			url: 'venda/totalPorMes',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
		
		
		$.ajax({
			url: 'venda/totalPorOrigem',
			method: 'GET',
			success: onDadosRecebidosVendaOrigem.bind(this)
		});
		
	}
	
	function onDadosRecebidos(vendaMes){
		var meses = [];
		var valores = [];
		vendaMes.forEach(function(obj){
			meses.unshift(obj.mes);
			valores.unshift(obj.total);
		});
		
		var graficoVendasPorMes = new Chart(this.ctx,{
			type: 'line',
			data: {
				labels: meses,
				datasets: [
					{
						label: 'Vendas por mês',
						backgroundColor: "rgba(26,179,148,0.5)",
						pointBorderColor: "rgba(26,179,148,1)",
						pointBackgroundColor: "#fff",
						data: valores
					}
				]
			},
		});
	}
	
	function onDadosRecebidosVendaOrigem(vendaMes){
		var meses = [];
		var valoresNacional = [];
		var valoresInternacional = [];
		vendaMes.forEach(function(obj){
			meses.unshift(obj.mes);
			valoresNacional.unshift(obj.totalNacional);
			valoresInternacional.unshift(obj.totalInternacional);
		});
		
		
		var graficoVendasPorOrigem = new Chart(this.ctxOrigem,{
			type: 'bar',
			data: {
				labels: meses,
				datasets: [
					{
						label: 'Vendas nacionais',
						backgroundColor: "rgba(26,179,148,0.5)",
						pointBorderColor: "rgba(26,179,148,1)",
						pointBackgroundColor: "#fff",
						data: valoresNacional
					},
					{
						label: 'Vendas internacionais',
						backgroundColor: "rgba(41,134,204,0.5)",
						pointBorderColor: "rgba(41,134,204,1)",
						pointBackgroundColor: "#fff",
						data: valoresInternacional
					}
				]
			},
		});
	}
	
	return GraficoVendaPorMes;
}());

$(function(){
	var graficoVendaPorMes = new Brewer.GraficoVendaPorMes();
	graficoVendaPorMes.iniciar();
});