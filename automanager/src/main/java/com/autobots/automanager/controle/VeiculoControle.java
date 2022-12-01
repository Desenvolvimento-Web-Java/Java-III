package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.modelos.SelecionadorUsuario;
import com.autobots.automanager.modelos.SelecionadorVeiculo;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {
	@Autowired
	private RepositorioUsuario repoUsuario;
	@Autowired
	private RepositorioVeiculo repoVeiculo;
	@Autowired
	private SelecionadorUsuario selecionadorUsu;
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
	
	@PostMapping ("/cadastro/{idUsuario}")
	public ResponseEntity<?> cadastroVeiculo(
		@PathVariable Long idUsuario,
		@RequestBody Veiculo body
		){
		List<Usuario> todos = repoUsuario.findAll();
		Usuario selecionados = selecionadorUsu.select(todos, idUsuario);
		if(selecionados != null) {
			selecionados.getVeiculos().add(body);
			body.setProprietario(selecionados);
			repoVeiculo.save(body);
			return new ResponseEntity<>("Veiculo cadastrado no usuario: " + selecionados.getNome(), HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>("Usuario não encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
