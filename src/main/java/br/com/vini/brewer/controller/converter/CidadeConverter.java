package br.com.vini.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.Cidade;

public class CidadeConverter implements Converter<String, Cidade>{

	@Override
	public Cidade convert(String value) {
		
		if(!StringUtils.isEmpty(value)) {
			Cidade cidade = new Cidade();
			cidade.setId(Long.valueOf(value));
			return cidade;
		}
		
		return null;
	}
	 
}
