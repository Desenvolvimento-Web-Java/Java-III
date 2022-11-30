package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Usuario;

@Component
public class SelecionadorUsuario implements Selecionador<Usuario, Long>{

	@Override
	public Usuario select(List<Usuario> busca, Long idJosias) {
		Usuario Selecionado = null;
		for (Usuario index: busca){
			if (index.getId().longValue() == idJosias.longValue()) {
				Selecionado = index;
				break;
			}
		}
		return Selecionado;
	}
	
}
