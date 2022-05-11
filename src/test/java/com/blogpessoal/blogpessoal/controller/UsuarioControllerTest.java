package com.blogpessoal.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
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

import com.blogpessoal.blogpessoal.model.Usuario;
import com.blogpessoal.blogpessoal.repository.UsuarioRepository;
import com.blogpessoal.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//indica em qual ordem os testes serão executados, conforme a @Order.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate; //feito para enviar as minhas requisições para nossa aplicação
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	//@BeforeEach Se você quiser limpar tudo antes de cada teste, ai sim 
	@BeforeAll //deleta uma vez antes de tudo
	void start() {
		usuarioRepository.deleteAll();
	}
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar um usuário")
	public void deveCriarUmUsuario() {
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"Ana Kélvia", "ana.k@gmail.com", "123456789", "http://foto.jpg"));
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange("usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
		assertEquals(requisicao.getBody().getFoto(), resposta.getBody().getFoto());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação de usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.CadastrarUsuario(new Usuario(0L, 
				"T'Challa Coutinho", "tchalla_cou@gmail.com", "123456789", "http://fotoT.jpg"));
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"T'Challa Coutinho", "tchalla_cou@gmail.com", "123456789", "http://fotoT.jpg"));
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange("usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	
	@Test
	@Order(3)
	@DisplayName("Alterar um usuário")
	public void deveAlterarUmUsuario() {
		Optional<Usuario> usuarioCreate = usuarioService.CadastrarUsuario(new Usuario(0L, 
				"Laura Sofia", "soph@gmail.com", "123456789", "http://fotoL.jpg"));
		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(),
				"Laura Sofia Marques", "soph_marques@gmail.com", "123456789", "http://fotoL.jpg");
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);
		ResponseEntity<Usuario> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("usuarios/cadastrar", HttpMethod.PUT, requisicao, Usuario.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
		assertEquals(usuarioUpdate.getFoto(), resposta.getBody().getFoto());
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os usuários")
	public void deveMostrarTodosUsuarios() {
		usuarioService.CadastrarUsuario(new Usuario(0L, 
				"Carol Denvers", "c.denvers@gmail.com", "123456789", "http://fotoC.jpg"));
		usuarioService.CadastrarUsuario(new Usuario(0L, 
				"Natasha Romanoff", "nath.romandoff@gmail.com", "123456789", "http://fotoN.jpg"));
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
	}

}
