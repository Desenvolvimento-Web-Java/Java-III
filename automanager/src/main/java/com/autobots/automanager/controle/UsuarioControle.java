package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.modelos.SelecionadorUsuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;


@RestController
@RequestMapping ("/usuario")
public class UsuarioControle {
	@Autowired 
	private RepositorioUsuario repoUser;
	@Autowired
	private SelecionadorUsuario selecionador;
	@GetMapping ("/todos")
	public ResponseEntity<?> pegarTodosUsuarios (){
		List<Usuario> todos = repoUser.findAll();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (todos.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}else {
			return new ResponseEntity<>(todos,status);
		}
		
	}
	@GetMapping ("/todos/{usuario}")
	public ResponseEntity<?> pegarUser (@PathVariable Long usuario){
		Usuario selecionado = selecionador.select(repoUser.findAll(), usuario);
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
