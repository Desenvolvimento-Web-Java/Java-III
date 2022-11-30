package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;


import com.autobots.automanager.entitades.Mercadoria;

@Component
public class SelecionadorMercadoria implements Selecionador<Mercadoria, Long>{

	@Override
	public Mercadoria select(List<Mercadoria> busca, Long idJosias) {
		Mercadoria Selecionado = null;
		for (Mercadoria index: busca){
			if (index.getId().longValue() == idJosias.longValue()) {
				Selecionado = index;
				break;
			}
		}
		return Selecionado;
	}

}
