package com.maia.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maia.cursomc.domain.Categoria;
import com.maia.cursomc.domain.Cidade;
import com.maia.cursomc.domain.Estado;
import com.maia.cursomc.domain.Produto;
import com.maia.cursomc.repositores.CategoriaRepository;
import com.maia.cursomc.repositores.CidadeRepository;
import com.maia.cursomc.repositores.EstadoRepository;
import com.maia.cursomc.repositores.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	// Dependencia do Projeto
	@Autowired
	CategoriaRepository repository;

	@Autowired
	ProdutoRepository pdtRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	EstadoRepository estadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	// *************

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

		// ********************

		// **Intaciando Estado e Cidade e Salvando os Mesmo no banco
		Estado est1 = new Estado(null, "Goiás");
		Estado est2 = new Estado(null, "Minas Gerais");

		Cidade c1 = new Cidade(null, "Goiania", est1);
		Cidade c2 = new Cidade(null, "Aparecida de Goiania", est1);
		Cidade c3 = new Cidade(null, "Belo Horizonte", est2);

		est1.getCidades().addAll(Arrays.asList(c1, c2));
		est2.getCidades().addAll(Arrays.asList(c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

	}

}
