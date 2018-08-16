package com.maia.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maia.cursomc.domain.Categoria;
import com.maia.cursomc.domain.Produto;
import com.maia.cursomc.repositores.CategoriaRepository;
import com.maia.cursomc.repositores.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	// Dependencia de Categoria
	@Autowired
	CategoriaRepository repository;
	// Dependencia de Produto
	@Autowired
	ProdutoRepository pdtRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Categorias
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Telefonia");

		// Produtos
		Produto p1 = new Produto(null, "Notbook S50", 6000.00);
		Produto p2 = new Produto(null, "Placa Grafica GTX1080TI", 5090.00);
		Produto p3 = new Produto(null, "Samsung S8 Plus", 2999.00);

		// Assoçiando a Listas
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p3));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		p3.getCategorias().addAll(Arrays.asList(cat2, cat1));

		// **Salvando as Listas de Produto e Categorias no BD
		repository.saveAll(Arrays.asList(cat1, cat2));
		pdtRepository.saveAll(Arrays.asList(p1, p2, p3));
	}

}
