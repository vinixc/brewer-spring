package br.com.vini.brewer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.vini.brewer.storage.FotoStorage;

@Component
public class CervejaListener {
	
	@Autowired
	private FotoStorage fotoStorage;

	@EventListener(condition = "#event.temFoto() && #event.novaFoto()")
	public void cervejaSalva(CervejaSalvaEvent event) {
		fotoStorage.salvar(event.getCerveja().getFoto());
	}
}
