package br.com.vini.brewer.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.vini.brewer.config.JPAConfig;
import br.com.vini.brewer.config.SecurityConfig;
import br.com.vini.brewer.config.ServiceConfig;
import br.com.vini.brewer.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {JPAConfig.class, ServiceConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{ WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		HttpPutFormContentFilter contentFilter = new HttpPutFormContentFilter();
		return new Filter[] {contentFilter};
		
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
}
