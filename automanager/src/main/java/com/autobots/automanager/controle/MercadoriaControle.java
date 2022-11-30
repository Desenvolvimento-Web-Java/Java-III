package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.modelos.SelecionadorMercadoria;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {
	@Autowired
	private RepositorioMercadoria repoMercadoria;
	@Autowired
	private SelecionadorMercadoria selecionador;
	@GetMapping ("/todos")
	public ResponseEntity<?> pegarTodasMercadorias (){
		List<Mercadoria> todos = repoMercadoria.findAll();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (todos.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}else {
			return new ResponseEntity<>(todos,status);
		}
		
	}
	@GetMapping ("/todos/{mercadoria}")
	public ResponseEntity<?> pegarMercadoria (@PathVariable Long mercadoria){
		Mercadoria selecionado = selecionador.select(repoMercadoria.findAll(), mercadoria);
		HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
		if (selecionado == null) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}else {
			status = HttpStatus.FOUND;
			return new ResponseEntity<>(selecionado, status);
		}
	}
}
