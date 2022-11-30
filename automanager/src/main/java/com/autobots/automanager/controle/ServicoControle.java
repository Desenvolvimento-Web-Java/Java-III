package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.modelos.SelecionadorServico;
import com.autobots.automanager.repositorios.RepositorioServico;

@RestController
@RequestMapping ("/servico")
public class ServicoControle {
	@Autowired
	private RepositorioServico repoServico;
	@Autowired
	private SelecionadorServico selecionador;
	@GetMapping ("/todos")
	public ResponseEntity<?> pegarTodosServicos (){
		List<Servico> todos = repoServico.findAll();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (todos.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}else {
			return new ResponseEntity<>(todos,status);
		}
		
	}
	@GetMapping ("/todos/{servico}")
	public ResponseEntity<?> pegarServico (@PathVariable Long servico){
		Servico selecionado = selecionador.select(repoServico.findAll(), servico);
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
