package br.com.vini.brewer.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.vini.brewer.model.Venda;

@Component
@PropertySource({"classpath:env/mail-${ambiente:local}.properties"})
@PropertySource(value = {"file:///srv/config/brewer-mail.properties"}, ignoreResourceNotFound = true)
public class Mailer {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Async
	public void enviar(Venda venda) {
		
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom(env.getProperty("mail.from"));
		mensagem.setTo(venda.getCliente().getEmail());
		mensagem.setSubject("Venda Efetuada");
		mensagem.setText("Obrigado, sua venda foi processada!");
		
		mailSender.send(mensagem);
	}
}
