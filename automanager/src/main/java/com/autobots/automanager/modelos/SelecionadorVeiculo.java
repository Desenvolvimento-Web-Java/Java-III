package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;


import com.autobots.automanager.entitades.Veiculo;

@Component
public class SelecionadorVeiculo implements Selecionador<Veiculo, Long>{

	@Override
	public Veiculo select(List<Veiculo> busca, Long idJosias) {
		Veiculo Selecionado = null;
		for (Veiculo index: busca){
			if (index.getId().longValue() == idJosias.longValue()) {
				Selecionado = index;
				break;
			}
		}
		return Selecionado;
	}

}
