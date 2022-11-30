package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Venda;

@Component
public class SelecionadorVenda implements Selecionador<Venda, Long>{

	@Override
	public Venda select(List<Venda> busca, Long idJosias) {
		Venda Selecionado = null;
		for (Venda index: busca){
			if (index.getId().longValue() == idJosias.longValue()) {
				Selecionado = index;
				break;
			}
		}
		return Selecionado;
		
	}

}
