package org.generation.blogPessoal.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start(){

		usuarioRepository.deleteAll();
		
		usuarioRepository.save(new Usuario(0L, "Samuel Santos", "samuel@email.com.br", "13465278"));
		
		usuarioRepository.save(new Usuario(0L, "Larissa Oliveira", "larissa@email.com.br", "13465278"));
		
		usuarioRepository.save(new Usuario(0L, "Ana Clara","ana@email.com.br", "13465278"));

        usuarioRepository.save(new Usuario(0L, "Douglas Vieira", "douglas@email.com.br", "13465278"));

	}
	
	@Test
	@DisplayName("Retorna 1 usuario especifico para testes")
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("samuel@email.com.br");
		assertTrue(usuario.get().getUsuario().equals("samuel@email.com.br"));
	}
	
	@Test
	@DisplayName("Retorna 4 usuarios para testes")
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Santos");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Samuel Santos"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Larissa Oliveira"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Ana Clara"));
		assertTrue(listaDeUsuarios.get(3).getNome().equals("Douglas Vieira"));
		
	}

}