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
import com.generation.crudfarmacia.model.Produto;
import com.generation.crudfarmacia.repository.CategoriaRepository;
import com.generation.crudfarmacia.repository.ProdutoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProdutoControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	private Categoria categoria;

	Categoria categoria1 = new Categoria(1L, "Perfumaria");
	Categoria categoria2 = new Categoria(2L, "Antidepressivos");
	Categoria categoria3 = new Categoria(3L, "Analgésicos");

	Produto produto1 = new Produto(1L, "Creme para Corpo", "Loção Hidratante Desodorante 400ml", 70.99f, 15,
			categoria1);
	Produto produto2 = new Produto(2L, "Rivotril", "Indicado para o alívio dos sintomas da ansiedade", 20.00f, 50,
			categoria2);
	Produto produto3 = new Produto(3L, "Dipirona", "Indicado para o alívio dos sintomas de dores", 19.99f, 30,
			categoria3);

	@BeforeAll
	void start() {

		produtoRepository.deleteAll();
		categoriaRepository.deleteAll();

		categoriaRepository.save(categoria1);
		categoriaRepository.save(categoria2);
		categoriaRepository.save(categoria3);

		produtoRepository.save(produto1);
		produtoRepository.save(produto2);
		produtoRepository.save(produto3);

	}

	@Test
	@DisplayName("Criar um produto")
	public void deveCriarproduto() {

		HttpEntity<Produto> corpoRequisicao = new HttpEntity<Produto>(produto1);

		produto1.setId(5L);

		ResponseEntity<Produto> resposta = testRestTemplate.exchange("/produtos", HttpMethod.POST, corpoRequisicao,
				Produto.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar um produto")
	public void deveAtualizarproduto() {
		Produto produto = produtoRepository.findById(1L).get();

		HttpEntity<Produto> corpoRequisicao = new HttpEntity<Produto>(produto);

		ResponseEntity<Produto> resposta = testRestTemplate.exchange("/produtos", HttpMethod.PUT, corpoRequisicao,
				Produto.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Listar todos os Produtos")
	public void deveMostrarTodosProdutos() {
		ResponseEntity<String> resposta = testRestTemplate.exchange("/produtos", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Deletar um Produto")
	public void deveDeletarProduto() {

		ResponseEntity<Produto> resposta = testRestTemplate.exchange("/produtos/3", HttpMethod.DELETE, null,
				Produto.class);

		assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
	}

}
