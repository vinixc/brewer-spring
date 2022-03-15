package br.com.vini.brewer.config.init;

import java.util.EnumSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer{

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		
//		determinando a duração da sessao do usuario, em segundos, acabando o tempo sera necessario novo login
//		servletContext.getSessionCookieConfig().setMaxAge(20);
		
//		Setando o key session id em cookie para nao aparecer na url
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
	}
}
