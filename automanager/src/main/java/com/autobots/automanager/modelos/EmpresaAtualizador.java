package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Empresa;


public class EmpresaAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();

	private void atualizarDados(Empresa cliente, Empresa atualizacao) {
		if (!verificador.verificar(atualizacao.getNomeFantasia())) {
			cliente.setNomeFantasia(atualizacao.getNomeFantasia());
		}
		if (!verificador.verificar(atualizacao.getRazaoSocial())) {
			cliente.setRazaoSocial(atualizacao.getRazaoSocial());
		}
	}

	public void atualizar(Empresa selecionado, Empresa atualizador) {
		atualizarDados(selecionado, atualizador);
		enderecoAtualizador.atualizar(selecionado.getEndereco(), atualizador.getEndereco());
		telefoneAtualizador.atualizar(selecionado.getTelefones(), atualizador.getTelefones());
	}
}
