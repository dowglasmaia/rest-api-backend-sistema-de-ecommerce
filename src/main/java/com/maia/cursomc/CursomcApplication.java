package com.maia.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maia.cursomc.domain.Categoria;
import com.maia.cursomc.domain.Cidade;
import com.maia.cursomc.domain.Cliente;
import com.maia.cursomc.domain.Endereco;
import com.maia.cursomc.domain.Estado;
import com.maia.cursomc.domain.Produto;
import com.maia.cursomc.domain.enums.TipoPessoa;
import com.maia.cursomc.repositores.CategoriaRepository;
import com.maia.cursomc.repositores.CidadeRepository;
import com.maia.cursomc.repositores.ClienteRepository;
import com.maia.cursomc.repositores.EnderecoRepository;
import com.maia.cursomc.repositores.EstadoRepository;
import com.maia.cursomc.repositores.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	// Dependencia do Projeto
	@Autowired
	private CategoriaRepository repository;

	@Autowired
	private ProdutoRepository pdtRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	// *************

	@Override
	public void run(String... args) throws Exception {
		// Povoando a Categoria
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Telefonia");

		// Povoando o Produto
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

		// **Povoando o Estado e Cidade e Salvando os Mesmo no banco
		Estado est1 = new Estado(null, "Goiás");
		Estado est2 = new Estado(null, "Minas Gerais");

		Cidade c1 = new Cidade(null, "Goiania", est1);
		Cidade c2 = new Cidade(null, "Aparecida de Goiania", est1);
		Cidade c3 = new Cidade(null, "Belo Horizonte", est2);

		est1.getCidades().addAll(Arrays.asList(c1, c2));
		est2.getCidades().addAll(Arrays.asList(c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		// **Povoando o Cliente no banco
		Cliente cli1 = new Cliente(null, "Dowglas Maia", "Dowglasmaia@live.com", "003.653.658-87",
				TipoPessoa.PESSOA_FISICA);

		cli1.getTelefones().addAll(Arrays.asList("62-98888-8987", "89-95555-8745"));

		Endereco e1 = new Endereco(null, "Rua k15", "20-B", "Apt-250", "Centro", "78.658-890", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Ventos Leva", "220-B", "Apt-350", "Centro", "68.658-890", cli1, c3);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

	}

}
