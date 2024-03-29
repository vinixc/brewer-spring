package br.com.vini.brewer.controller.validator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.vini.brewer.model.Cerveja;
import br.com.vini.brewer.model.Venda;
import br.com.vini.brewer.repository.CervejaRepository;

@Component
public class VendaValidator implements Validator{

	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Venda.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "cliente.id", "", "Selecione um cliente na pesquisa rápida");
		
		Venda venda = (Venda) target;
		
		validarSeInformouApenasHorarioDaEntrega(errors, venda);
		validarSeInformouItens(errors, venda);
		validarValorTotalNegativo(errors, venda);
		validarQuantidadeEstoque(errors,venda);
	}

	private void validarQuantidadeEstoque(Errors errors, Venda venda) {
		venda.getItens().forEach(i -> {
			Cerveja cerveja = cervejaRepository.findOne(i.getCerveja().getId());
			if(cerveja.getQuantidadeEstoque() < i.getQuantidade()) {
				errors.reject("", String.format("Cerveja %s sem quantidade suficiente para atender o pedido! [Quantidade em estoque %s]", cerveja.getNome(), cerveja.getQuantidadeEstoque()));
			}
		});
	}

	private void validarValorTotalNegativo(Errors errors, Venda venda) {
		if(venda.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
			errors.reject("", "Valor total não pode ser negativo!");
		}
	}

	private void validarSeInformouItens(Errors errors, Venda venda) {
		if(venda.getItens().isEmpty()) {
			errors.reject("","Adicione pelo menos uma cerveja na venda");
		}
	}

	private void validarSeInformouApenasHorarioDaEntrega(Errors errors, Venda venda) {
		if(venda.getHoraEntrega() != null && venda.getDataEntrega() == null) {
			errors.rejectValue("dataEntrega", "", "Informe uma data da entrega para um horário");
		}
	}

}
