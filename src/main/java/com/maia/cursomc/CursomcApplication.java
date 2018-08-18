package com.maia.cursomc;

import java.text.SimpleDateFormat;
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
import com.maia.cursomc.domain.Pagamento;
import com.maia.cursomc.domain.Pedido;
import com.maia.cursomc.domain.PgtoBoleto;
import com.maia.cursomc.domain.PgtoCartao;
import com.maia.cursomc.domain.Produto;
import com.maia.cursomc.domain.enums.EstadoPgto;
import com.maia.cursomc.domain.enums.TipoPessoa;
import com.maia.cursomc.repositores.CategoriaRepository;
import com.maia.cursomc.repositores.CidadeRepository;
import com.maia.cursomc.repositores.ClienteRepository;
import com.maia.cursomc.repositores.EnderecoRepository;
import com.maia.cursomc.repositores.EstadoRepository;
import com.maia.cursomc.repositores.PagamentoRepository;
import com.maia.cursomc.repositores.PedidoRepository;
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

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

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
		// ****

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// **Povoando o Pedido
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/09/2017 10:30"), cli1, e2);

		Pagamento pgt1 = new PgtoCartao(null, EstadoPgto.QUITADO, ped1, 6);
		ped1.setPagamento(pgt1);

		Pagamento pgt2 = new PgtoBoleto(null, EstadoPgto.PENDENTE, ped2, sdf.parse("19/10/2017 00:00"), null);
		ped2.setPagamento(pgt2);

		// associar os clientes ao pedidos do mesmo
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgt1, pgt2));

	}

}
