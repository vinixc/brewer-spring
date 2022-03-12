package br.com.vini.brewer.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ObjectUtils;

import br.com.vini.brewer.validation.AtributoConfirmacao;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object>{

	private String atributo;
	private String atributoConfirmacao;
	private String atributoParaValidarAlteracao;
	
	@Override
	public void initialize(AtributoConfirmacao constraintAnnotation) {
		this.atributo = constraintAnnotation.atributo();
		this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
		this.atributoParaValidarAlteracao = constraintAnnotation.atributoParaValidarAlteracao();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		Boolean valido = false;
		try {

			Object valorAtributo = BeanUtils.getProperty(object, this.atributo);
			Object valorAtributoConfirmacao = BeanUtils.getProperty(object, this.atributoConfirmacao);
			
			Object valorAtributoValidarAlteracao = null;
			if(this.atributoParaValidarAlteracao != null && !this.atributoParaValidarAlteracao.trim().isEmpty()) {
				valorAtributoValidarAlteracao = BeanUtils.getProperty(object,this.atributoParaValidarAlteracao);
			}
			
			valido = ambosSaoNull(valorAtributo,valorAtributoConfirmacao,valorAtributoValidarAlteracao) || ambosSaoIguais(valorAtributo, valorAtributoConfirmacao);
			
		}catch (Exception e) {
			throw new RuntimeException("Error recuperando valores dos atributos", e);
		}
		
		if(!valido) {
			context.disableDefaultConstraintViolation();
			String mensagem = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
			violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
		}
		
		return valido;
	}

	private boolean ambosSaoIguais(Object valorAtributo, Object valorAtributoConfirmacao) {
		return !ObjectUtils.isEmpty(valorAtributo) && valorAtributo.equals(valorAtributoConfirmacao);
	}

	private Boolean ambosSaoNull(Object valorAtributo, Object valorAtributoConfirmacao, Object valorAtributoValidarAlteracao) {
		return ObjectUtils.isEmpty(valorAtributo) && ObjectUtils.isEmpty(valorAtributoConfirmacao) && !ObjectUtils.isEmpty(valorAtributoValidarAlteracao);
	}

}
