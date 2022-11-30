package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.modelos.SelecionadorVeiculo;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {
	@Autowired
	private RepositorioVeiculo repoVeiculo;
	@Autowired
	private SelecionadorVeiculo selecionador;
	@GetMapping ("/todos")
	public ResponseEntity<?> pegarTodosVeiculos (){
		List<Veiculo> todos = repoVeiculo.findAll();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (todos.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}else {
			return new ResponseEntity<>(todos,status);
		}
		
	}
	@GetMapping ("/todos/{veiculo}")
	public ResponseEntity<?> pegarVeiculo (@PathVariable Long veiculo){
		Veiculo selecionado = selecionador.select(repoVeiculo.findAll(), veiculo);
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
