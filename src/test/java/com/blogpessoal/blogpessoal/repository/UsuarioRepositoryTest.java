package com.blogpessoal.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.blogpessoal.blogpessoal.model.Usuario;

/*a notação indica que essa classse é uma classe spring boot teste, e a enviroment indica que se
a localhost tiver ocupada o spring vai indicar uma nova porta automaticamente*/
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Essa notação indica que o ciclo de vida da classe teste será por classe
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start()
	{
		usuarioRepository.deleteAll();
		usuarioRepository.save(new Usuario(0L, "Zé Colmeia", "roba_lanche@email.com", "123456789", "https://image1.com/seilaonde.jpg"));
		usuarioRepository.save(new Usuario(0L, "Harry Potter", "HarryGrinffindor@email.com", "987654321", "https://image2.com/harry.jpg"));
		usuarioRepository.save(new Usuario(0L, "Anakin Skywalker", "sky_ani@email.com", "214536987", "https://image3.com/ani.jpg"));
		usuarioRepository.save(new Usuario(0L, "Ahsoka Tano", "Ahsoka@email.com", "852147963", "https://image4.com/ahsoka.jpg"));
	}
	@Test
	@DisplayName("Retorna 1 usuário")
	public void deveRetornarUmUsuario()
	{
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("Ahsoka@email.com");
		assertTrue(usuario.get().getUsuario().equals("Ahsoka@email.com"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuário")
	public void deveRetornarTresUsuario()
	{
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Colmeia");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Zé Colmeia"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Harry Potter"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Anakin Skywalker"));
	}

}
