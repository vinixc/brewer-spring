package br.com.vini.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.vini.brewer.service.CadastroCervejaService;
import br.com.vini.brewer.storage.FotoStorage;
import br.com.vini.brewer.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses = CadastroCervejaService.class)
public class ServiceConfig {
	
	@Bean
	public FotoStorage fotoStorageLocal() {
		return new FotoStorageLocal();
	}

}
