package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.generation.blogPessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	
   /**
	* English:
	* We requested @Autowired because it will be responsible for doing a dependency injection of the UsuarioService Service class, 
	* so we have all the * access to the necessary CRUD methods.
	* 
	* Portuguese:
	* Solicitamos o @Autowired pois ele será resposavel por fazer uma injeção de dependência da classe de Serviço UsuarioService, 
	* para termos todo acesso aos métodos necessarios do CRUD.
	* 
	* @author Samuel
	* @since final version 03/13/22(eng); versão final 13/03/22(pt-br);
	*/
	@Autowired
	private UsuarioService service;

	@Autowired
	private UsuarioRepository repository;

	/**
	 * English:
     * We use the method @GetMapping("/all") to be able to check the selection of records that we have in our system, where it
     * will return all existing data. 
     * 
     * The 'id' end-point will search for id, 'logar' will check if it's ok, if not, it doesn't authorize login,
     * the 'cadastrar' allows you to create a registration or does not leave a bad request,
     * the 'atualizar' allows updating an existing user or does not leave a bad request.
	 * 
  	 * Portuguese:
	 * Utilizamos o metodo @GetMapping("/all") para podermos conferir a selecao de cadastros que possuimos em nosso sistema, onde ele 
	 * irá retornar todos dados existentes. 
	 * 
	 * O end-point de 'id' fará uma busca por id, o 'logar' irá checar se está ok, se nao estiver nao autoriza logar, 
	 * o 'cadastrar' permite fazer a criação de cadastro ou nao deixa dando um bad request, 
	 * o 'atualizar' permite atualizar um usuario existente ou nao deixa dando um bad request.
	 * 
	 * 
	 * @author Samuel
     * @since final version 03/13/22(eng); versão final 13/03/22(pt-br);
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity <List<Usuario>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable long id) {
		return repository.findById(id)
			.map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/logar")
	public ResponseEntity<UserLogin> autenticationUsuario(@RequestBody Optional<UserLogin> usuario) {
		return service.logarUsuario(usuario)
			.map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@Valid @RequestBody Usuario usuario) {
		return service.cadastrarUsuario(usuario)
			.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario){
		return service.atualizarUsuario(usuario)
			.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

}
