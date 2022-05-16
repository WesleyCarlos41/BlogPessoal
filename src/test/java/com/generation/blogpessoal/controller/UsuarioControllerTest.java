package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private UsuarioService usuarioService;
	
	@Test
	@Order(1)
	@DisplayName("Cadastra um usuario")
	public void deveCriarUmUsuario() {
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L,
				"Wesleyy Carlos", "wesleyy@gmail.com","1234567", "foto"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate.
				exchange("/usuario/cadastrar", HttpMethod.POST,requisicao, Usuario.class);
	
		assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(),resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(),resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação de usuario")
	public void naoDeveDuplicarUsuario() {
		
		usuarioService.cadastraUsuario(new Usuario(0L, "Caique da silva",
				"caique@gmail.com", "caique123","foto"));
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L,
				"Caique da silva", "caique@gmail.com", "caique123","foto"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange
				("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	@Test
	@Order(3)
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {
		
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastraUsuario(new Usuario(0L, 
				"Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", "foto"));
	
		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), 
				"Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana123" ,"foto");
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
		
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {
		
		usuarioService.cadastraUsuario(new Usuario(0L, 
				"Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", "foto"));
			
			usuarioService.cadastraUsuario(new Usuario(0L, 
				"Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", "foto"));
			
			ResponseEntity<String> resposta = testRestTemplate
					.withBasicAuth("root", "root")
					.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
			assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@Order(5)
	@DisplayName("Listar Um Usuário Específico")
	public void deveListarApenasUmUsuario() {
		
		Optional<Usuario> usuarioBusca = usuarioService.cadastraUsuario(new Usuario(0L, 
				"Laura Santolia", "laura_santolia@email.com.br", "laura12345", "foto"));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/" + usuarioBusca.get().getId(), HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
		
}
