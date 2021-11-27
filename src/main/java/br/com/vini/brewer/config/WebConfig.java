package br.com.vini.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.vini.brewer.controller.CervejaController;

@Configuration
@ComponentScan(basePackageClasses = {CervejaController.class})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{

}
