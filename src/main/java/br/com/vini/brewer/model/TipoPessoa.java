package br.com.vini.brewer.model;

import br.com.vini.brewer.model.validation.group.CpfGroup;
import br.com.vini.brewer.model.validation.group.CnpjGroup;

public enum TipoPessoa {

	FISICA("Física","CPF","000.000.000-00", CpfGroup.class),
	JURIDICA("Jurídica", "CNPJ","00.000.000/0000-00", CnpjGroup.class);
	
	private String descricao;
	private String documento;
	private String mascara;
	private Class<?> grupo;

	private TipoPessoa(String descricao, String documento, String mascara, Class<?> clazz) {
		this.descricao = descricao;
		this.documento = documento;
		this.mascara = mascara;
		grupo = clazz;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDocumento() {
		return documento;
	}

	public String getMascara() {
		return mascara;
	}

	public Class<?> getGrupo() {
		return grupo;
	}
	
	public static String removerFormatacao(String cpfOuCnpj) {
		return cpfOuCnpj.replaceAll("\\.|-|/", "");
	}
}
