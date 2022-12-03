package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Usuario;

public class UsuarioAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();

	private void atualizarDados(Usuario cliente, Usuario atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			cliente.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			cliente.setNomeSocial(atualizacao.getNomeSocial());
		}
	}

	public void atualizar(Usuario cliente, Usuario atualizacao) {
		enderecoAtualizador.atualizar(cliente.getEndereco(), atualizacao.getEndereco());
		atualizarDados(cliente, atualizacao);
		telefoneAtualizador.atualizar(cliente.getTelefones(), atualizacao.getTelefones());
	}
}
