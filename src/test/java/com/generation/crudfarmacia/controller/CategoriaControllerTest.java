package com.generation.crudfarmacia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.crudfarmacia.model.Categoria;
import com.generation.crudfarmacia.repository.CategoriaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoriaControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
		
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@BeforeAll
	void start() {
		categoriaRepository.deleteAll();
        categoriaRepository.save(new Categoria(1L, "Perfumaria"));
		categoriaRepository.save(new Categoria(2L, "Antidepressivo"));
		categoriaRepository.save(new Categoria(3L, "Analgésicos"));
	}

	@Test
	@DisplayName("Cadastrar uma Categoria")
	public void criarUmaCategoria() {

		HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(
				new Categoria(0L, "Perfumaria"));

		ResponseEntity<Categoria> corpoResposta = testRestTemplate.exchange("/categorias", HttpMethod.POST,
				corpoRequisicao, Categoria.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	}
	
		
	@Test
	@DisplayName("Atualizar uma Categoria")
	public void deveAtualizarUmaCategoria() {
		
		Categoria categoria = new Categoria(1L, "Perfumaria e Cosméticos");
		
		HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(categoria);
		
		ResponseEntity<Categoria> corpoResposta = testRestTemplate
				.exchange("/categorias", HttpMethod.PUT, corpoRequisicao, Categoria.class);
		
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Listar todas as Categorias")
	public void deveMostrarTodasCategorias() {
	    ResponseEntity<String> resposta = testRestTemplate
	    		.exchange("/categorias", HttpMethod.GET, null, String.class);
	    
	        assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Deletar uma Categoria")
	public void deveDeletarCategoria() {

		ResponseEntity<Categoria> resposta = testRestTemplate.exchange("/categorias/3", HttpMethod.DELETE, null,
				Categoria.class);

		assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
	}
}