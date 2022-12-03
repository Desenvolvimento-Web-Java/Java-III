package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.modelos.SelecionadorEmpresa;
import com.autobots.automanager.modelos.SelecionadorUsuario;
import com.autobots.automanager.modelos.UsuarioAtualizador;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioTelefone;
import com.autobots.automanager.repositorios.RepositorioUsuario;


@RestController
@RequestMapping ("/usuario")
public class UsuarioControle {
	@Autowired 
	private SelecionadorEmpresa selecionadorEmp;
	@Autowired 
	private RepositorioTelefone repoTel;
	@Autowired
	private RepositorioUsuario repoUser;
	@Autowired
	private RepositorioEmpresa repoEmpresa;
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
	
	@PutMapping ("/atualizar/{id}")
	public ResponseEntity<?> atualizaUser (@PathVariable Long id, @RequestBody Usuario atualizador){
		Usuario selecionado = selecionador.select(repoUser.findAll(), id);
		HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
		if (selecionado == null) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}else {
			selecionado.setNome(atualizador.getNome());
			selecionado.setNomeSocial(atualizador.getNomeSocial());
			UsuarioAtualizador att = new UsuarioAtualizador();								
			att.atualizar(selecionado, atualizador);
			repoUser.save(selecionado);
			status = HttpStatus.FOUND;
			return new ResponseEntity<>(selecionado, status);
		}
			
}
		
				
	@PostMapping ("/cadastro/{idEmpresa}")
	public ResponseEntity<?> cadastrarUsuario(
	 @PathVariable Long idEmpresa,
	 @RequestBody Usuario cadastro	
	){
		List<Empresa> empresas = repoEmpresa.findAll();
		SelecionadorEmpresa selecionadorEmp = new SelecionadorEmpresa();
		Empresa main = selecionadorEmp.select(empresas, idEmpresa);
		if (main != null) {
			main.getUsuarios().add(cadastro);
			repoEmpresa.save(main);
			return new ResponseEntity<>("Cadastro efetuado!", HttpStatus.CREATED);
		}else {
	    	return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
	    }
	}
	
}

