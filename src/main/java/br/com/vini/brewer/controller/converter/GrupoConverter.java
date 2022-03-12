package br.com.vini.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.Grupo;

public class GrupoConverter implements Converter<String, Grupo>{

	@Override
	public Grupo convert(String source) {
		if(!StringUtils.isEmpty(source)) {
			Grupo grupo = new Grupo();
			grupo.setId(Long.valueOf(source));
			return grupo;
		}
		return null;
	}

}
