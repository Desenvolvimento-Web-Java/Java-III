package com.autobots.automanager.modelos;

import java.util.List;

public interface Selecionador <Seleciona,ID>{
	public Seleciona select(List<Seleciona> busca, ID idJosias);
}
