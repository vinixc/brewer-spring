package br.com.vini.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public String salvarTemporariamente(MultipartFile[] files);
	public void salvar(String foto);

	public byte[] recuperarFotoTemporaria(String nomeFoto);
	public byte[] recuperarFoto(String nome);
	public byte[] recuperarThumbnail(String foto);
}
