package com.generation.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.generation.blogpessoal.model.Usuario;

//indicando que a classe UsuarioRepositoryTest é uma classe de test,
//que vai rodar em uma porta aleatoria a cada teste realizado
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

//cria uma instancia de testes, que define que o ciclo de vida do teste vai respeitar o ciclo de vida da classe
//(será executado e resetado após o uso)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository repository;

	@BeforeAll
	void start() {
		
		repository.save(new Usuario(0L, "wesley", "wely@gmail.com", "wesley123","https://i.imgur.com/FETvs2O.jpg\r\n"));
		
		repository.save(new Usuario(0L, "Michael", "michaeltrimundial@gmail.com","nunca fui rebaixado","https://i.imgur.com/FETvs2O.jpg"));
		
		repository.save(new Usuario(0L, "Brocco", "broco@gmail.com","broccolis","https://i.imgur.com/FETvs2O.jpg"));
		
		repository.save(new Usuario(0L, "Mayara", "will31smith@gmail.com","cenoura","https://i.imgur.com/FETvs2O.jpg"));

	}
	
	@Test
	@DisplayName("Teste que retorna 1 usuario")
	public void retornaUmUsuario() {
		Optional<Usuario> usuario = repository.findByUsuario("wely@gmail.com");
		assertTrue(usuario.get().getUsuario().equals("wely@gmail.com"));
	}
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void retornarTresUsuarios() {
	
		List<Usuario> listaDeUsuarios = repository.findAllByNomeContainingIgnoreCase("Sousa");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Ricardo Sousa"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Carlos Sousa"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Caique Sousa"));
	}
	
	@AfterAll
	public void end() {
		repository.deleteAll();
		}
	
		
	}

