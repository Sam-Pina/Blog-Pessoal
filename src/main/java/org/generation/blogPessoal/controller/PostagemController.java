package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagemController {
    
	@Autowired
	private PostagemRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	/**
	 * English: 
	 * When passing a 'GET' type request in '/postagens' and passing some attribute, in this case an 'id', 
	 * this method will be accessed directly, where it will capture which variable we are receiving within the 'PathVariable'
	 * and we will return to our interface that we injected with 'autowired', we'll call the 'findById' method, it can return
	 * you either a post object or a 'notFound' object if there is an error in the request.
	 * 
	 * Portuguese:
	 * Ao passar uma requisição do tipo 'GET' em '/postagens' e passar algum atributo, neste caso um 'id', 
	 * este método será acessado diretamente, onde ele irá capturar qual variável estamos recebendo dentro da 'PathVariable' 
	 * e iremos retornar à nossa interface que injetamos com 'autowired', chamaremos o método 'findById', ele pode retornar 
	 * um objeto post ou um objeto 'notFound' se houver um erro na solicitação.
	 * 
	 * @param id
	 * @return
	 * @author Samuel
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	/**
	 * English: 
	 * We use this method to make an end-point get, this time one end-point per title, as if it were a sub-route to avoid 
	 * duplication with the above method. We use the method that is inside the 'repository', a method that we made ourselves 
	 * to bring all the values that we typed, testing them in Postman.
	 * 
	 * Portuguese:
	 * Usamos esse metodo para fazer um end-point get, dessa vez um end-point por titulo, como se fosse uma sub rota para não ocorrer
	 * duplicidade com metodo acima. Usamos o metodo que está dentro do 'repository', um metodo que nos mesmos fizemos para trazer
	 * todos valores que nos digitamos, testanto eles no Postman.
	 * 
	 * @param titulo
	 * @return
	 * @author Samuel
	 */
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post(@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> put(@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	/**
	 * English: 
	 * We implement the 'delete' method as a void, that is, it does not return anything, we take what comes from the URI as a parameter,
	 * in this case using the 'PathVariable', we capture the 'id' inside the 'long' variable, we call the repository passing the fear 
	 * 'deleteById'. To delete it is necessary to put the id you want in the URL.
	 * 
	 * Portuguese:
	 * Implementamos o metodo 'delete' como um void, ou seja nao retorna nada, pegamos o que vem da URI como parametro, 
	 * no caso utilizando o 'PathVariable', capturamos o 'id' dentro da variavel 'long', chamamos o repository passando o medoto 
	 * 'deleteById'. Para deletar é precisso colocar na URL  o id que deseja.
	 * 
	 * @param id
	 * @author Samuel
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id){
		repository.deleteById(id);
	}
	
}
