package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Empresa;


@Component
public class SelecionadorEmpresa implements Selecionador<Empresa, Long>{

	@Override
	public Empresa select(List<Empresa> busca, Long idJosias) {
		Empresa Selecionado = null;
		for (Empresa index: busca){
			if (index.getId().longValue() == idJosias.longValue()) {
				Selecionado = index;
				break;
			}
		}
		return Selecionado;
		
	}

}
