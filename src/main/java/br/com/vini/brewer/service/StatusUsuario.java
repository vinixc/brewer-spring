package br.com.vini.brewer.service;

import br.com.vini.brewer.repository.UsuarioRepository;

public enum StatusUsuario {
	
	ATIVAR {
		@Override
		public void executar(Long[] ids, UsuarioRepository repository) {
			repository.findByIdIn(ids).forEach(usuario -> usuario.setAtivo(true));
		}
	},
	DESATIVAR {
		@Override
		public void executar(Long[] ids, UsuarioRepository repository) {
			repository.findByIdIn(ids).forEach(usuario -> usuario.setAtivo(false));
		}
	};
	
	public abstract void executar(Long[] ids, UsuarioRepository repository);

}
