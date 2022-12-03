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

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.SelecionadorEmpresa;
import com.autobots.automanager.modelos.SelecionadorVenda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/venda")
public class VendaControle {
	@Autowired
	private RepositorioVenda repoVenda;
	@Autowired
	private RepositorioUsuario repoUser;
	@Autowired
	private RepositorioMercadoria repoMercadoria;
	@Autowired
	private RepositorioServico repoServico;
	@Autowired
	private RepositorioVeiculo repoVeiculo;
	@Autowired
	private RepositorioEmpresa repoEmpresa;
	@Autowired
	private SelecionadorEmpresa selecionadorEmpresa;
	@Autowired
	private SelecionadorVenda selecionador;
	@GetMapping ("/todos")
	public ResponseEntity<?> pegarTodasVendas (){
		List<Venda> todos = repoVenda.findAll();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (todos.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}else {
			return new ResponseEntity<>(todos,status);
		}
		
	}
	@GetMapping ("/todos/{venda}")
	public ResponseEntity<?> pegarVenda (@PathVariable Long venda){
		Venda selecionado = selecionador.select(repoVenda.findAll(), venda);
		HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
		if (selecionado == null) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}else {
			status = HttpStatus.FOUND;
			return new ResponseEntity<>(selecionado, status);
		}
	}
	
	@PostMapping ("/cadastroVenda/{empresa}")
	public ResponseEntity<?>cadastrarVenda (@PathVariable Long empresa, @RequestBody Venda vend){
		List<Empresa> empresas = repoEmpresa.findAll();
		Empresa selecionado = selecionadorEmpresa.select(empresas, empresa);
		if (selecionado == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else { 
			
			Usuario selectCli = repoUser.getById(vend.getCliente().getId());
			vend.setCliente(selectCli);
			Usuario selectFunc = repoUser.getById(vend.getFuncionario().getId());
			vend.setFuncionario(selectFunc);
//			for (Mercadoria merc: vend.getMercadorias()) {
//				for (Mercadoria merca: repoMercadoria.findAll()) {
//					if(merca.getId().equals(merc.getId())) {
//						Mercadoria selectMerc = repoMercadoria.getById(merc.getId());
//						vend.getMercadorias().add(merca);
//						break;
//					}
//				}
//				
//			}
//			for (Servico serv: vend.getServicos()) {
//				for(Servico nome:repoServico.findAll()) {
//					if(nome.getId().equals(serv.getId())){
//						Servico selectServ = repoServico.getById(serv.getId());
//						vend.getServicos().add(nome);
//						break;
//					}
//				}
//			
//			}
			System.out.println(vend.getServicos().size());
			Veiculo selectVeiculo = repoVeiculo.getById(vend.getVeiculo().getId());
			vend.setVeiculo(selectVeiculo);
			System.out.println(selectCli);
			selecionado.getVendas().add(vend);
			repoEmpresa.save(selecionado);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}
}
