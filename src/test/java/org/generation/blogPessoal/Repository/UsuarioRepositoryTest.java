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
		
		usuarioRepository.save(new Usuario(0L, "Samuel Santos", "samuel@email.com.br","https://i.imgur.com/Sk5SjWE.jpg", "13465278", "normal"));
		
		usuarioRepository.save(new Usuario(0L, "Larissa Santos", "larissa@email.com.br","https://i.imgur.com/yDRVeK7.jpg", "13465278","normal"));
		
		usuarioRepository.save(new Usuario(0L, "Ana Clara","ana@email.com.br","https://i.imgur.com/yDRVeK7.jpg", "13465278","normal"));

        usuarioRepository.save(new Usuario(0L, "Douglas Santos", "douglas@email.com.br","https://i.imgur.com/Sk5SjWE.jpg", "13465278","normal"));

	}
	
	@Test
	@DisplayName("Retorna 1 usuario especifico para testes")
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("samuel@email.com.br");
		assertTrue(usuario.get().getUsuario().equals("samuel@email.com.br"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios para testes")
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Santos");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Samuel Santos"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Larissa Santos"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Douglas Santos"));
	}

}
