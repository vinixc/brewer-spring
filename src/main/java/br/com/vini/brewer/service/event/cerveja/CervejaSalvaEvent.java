package br.com.vini.brewer.service.event.cerveja;

import org.springframework.util.StringUtils;

import br.com.vini.brewer.model.Cerveja;

public class CervejaSalvaEvent {
	
	private Cerveja cerveja;

	public CervejaSalvaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	public Cerveja getCerveja() {
		return cerveja;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(cerveja.getFoto());
	}
	
	public boolean novaFoto() {
		return cerveja.isNovaFoto();
	}
}
