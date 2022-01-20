package br.com.vini.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult<String> resultado;

	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<String> resultado) {
		this.files = files;
		this.resultado = resultado;
	}

	@Override
	public void run() {
		
		System.out.println("Recebendo fotos: " + files.length);
		//TODO: Salvar a foto no sistema de arquivos ....
		resultado.setResult("OK! Foto recebida!");
	}

}
