package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Servico;

@Component
public class SelecionadorServico implements Selecionador<Servico, Long>{

	@Override
	public Servico select(List<Servico> busca, Long idJosias) {
		Servico Selecionado = null;
		for (Servico index: busca){
			if (index.getId().longValue() == idJosias.longValue()) {
				Selecionado = index;
				break;
			}
		}
		return Selecionado;
		
	}
	
}
