
var Brewer = Brewer || {};

Brewer.MaskMoney = (function(){
	
	function MaskMoney(){
		 this.decimal = $('.js-decimal');
		 this.plain = $('.js-plain');
	}
	
	MaskMoney.prototype.enable = function(){
		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
	
	 	this.plain.maskMoney({ precision: 0, thousands: '.' });
	}
	
	return MaskMoney;
	
}());

Brewer.MaskPhoneNumber = (function(){
	
	function MaskPhoneNumber(){
		this.inputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function(){
		var maskBehavior = function (val){
			return val.replace(/\D/g,'').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		}
		
		var options = {
			onKeyPress: function(val, e, field, options){
				field.mask(maskBehavior.apply({},arguments), options);
			}
		};
		
		this.inputPhoneNumber.mask(maskBehavior,options);
	}
	
	return MaskPhoneNumber;
}());

Brewer.MaskCEP = (function(){
	
	function MaskCEP(){
		this.inputCep = $('.js-cep');
	}
	
	MaskCEP.prototype.enable = function(){
		var maskBehavior = function (){
			return '00000-000';
		};
		
		var options = {
			onKeyPress: function(val,e,field,options){
				field.mask(maskBehavior.apply({},arguments),options);
			}
		}
		
		this.inputCep.mask(maskBehavior,options);
	}
	
	return MaskCEP;
	
}());

Brewer.MaskDate = (function(){
	
	function MaskDate(){
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable = function(){
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true
		});
	}
	
	return MaskDate;
}());

Brewer.Security = (function(){
		
	function Security(){
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();	
	}
	
	Security.prototype.enable = function(){
		$(document).ajaxSend(function(event, jqxhr,settings){
			jqxhr.setRequestHeader(this.header,this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

Brewer.formatarMoeda = function(valor){
	return numeral(valor).format('0,0.00');
}

$(function(){
	
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskCEP = new Brewer.MaskCEP();
	maskCEP.enable();
	
	var maskDate = new Brewer.MaskDate();
	maskDate.enable();
	
	var security = new Brewer.Security();
	security.enable();
})