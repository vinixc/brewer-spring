package br.com.vini.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public String salvarTemporariamente(MultipartFile[] files);

}
