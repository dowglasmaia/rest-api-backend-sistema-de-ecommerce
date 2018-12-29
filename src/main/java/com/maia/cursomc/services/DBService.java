package com.maia.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Categoria;
import com.maia.cursomc.domain.Cidade;
import com.maia.cursomc.domain.Cliente;
import com.maia.cursomc.domain.Endereco;
import com.maia.cursomc.domain.Estado;
import com.maia.cursomc.domain.ItemPedido;
import com.maia.cursomc.domain.Pagamento;
import com.maia.cursomc.domain.Pedido;
import com.maia.cursomc.domain.PgtoBoleto;
import com.maia.cursomc.domain.PgtoCartao;
import com.maia.cursomc.domain.Produto;
import com.maia.cursomc.domain.enums.EstadoPgto;
import com.maia.cursomc.domain.enums.Perfil;
import com.maia.cursomc.domain.enums.TipoPessoa;
import com.maia.cursomc.repositores.CategoriaRepository;
import com.maia.cursomc.repositores.CidadeRepository;
import com.maia.cursomc.repositores.ClienteRepository;
import com.maia.cursomc.repositores.EnderecoRepository;
import com.maia.cursomc.repositores.EstadoRepository;
import com.maia.cursomc.repositores.ItemPedidoRepository;
import com.maia.cursomc.repositores.PagamentoRepository;
import com.maia.cursomc.repositores.PedidoRepository;
import com.maia.cursomc.repositores.ProdutoRepository;

@Service
public class DBService {

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

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe; // para encodar a Senha no banco de dados

	public void instanciateTestDatabase() throws ParseException {
		// Povoando a Categoria
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Telefonia");
		Categoria cat3 = new Categoria(null, "Energia");
		Categoria cat4 = new Categoria(null, "Acessorios");
		Categoria cat5 = new Categoria(null, "RH");
		Categoria cat6 = new Categoria(null, "Perfumaria");
		Categoria cat7 = new Categoria(null, "Audio e Video");
		Categoria cat8 = new Categoria(null, "Moveis");

		// Povoando o Produto
		Produto p1 = new Produto(null, "Notbook S50", 6000.00);
		Produto p2 = new Produto(null, "Placa Grafica GTX1080TI", 5090.00);
		Produto p3 = new Produto(null, "Samsung S8 Plus", 2999.00);
		Produto p4 = new Produto(null, "Notbook S501 Pro", 9000.00);
		Produto p5 = new Produto(null, "GTX1080", 2090.00);
		Produto p6 = new Produto(null, "Mesa Para PC", 999.00);
		Produto p7 = new Produto(null, "SSD 400 Gb", 600.00);
		Produto p8 = new Produto(null, "TV 49 Pol 4K", 3090.00);
		Produto p9 = new Produto(null, "Carregador S2 Plus", 99.00);

		// Assoçiando a Listas
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3, p4, p5, p7));
		cat2.getProdutos().addAll(Arrays.asList(p3));
		cat4.getProdutos().addAll(Arrays.asList(p9, p7, p5, p2));
		cat8.getProdutos().addAll(Arrays.asList(p6));
		cat7.getProdutos().addAll(Arrays.asList(p8));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat2, cat1));
		p4.getCategorias().addAll(Arrays.asList(cat1));
		p5.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p6.getCategorias().addAll(Arrays.asList(cat8));
		p7.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p8.getCategorias().addAll(Arrays.asList(cat7));
		p9.getCategorias().addAll(Arrays.asList(cat4));

		// **Salvando as Listas de Produto e Categorias no BD
		repository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8));
		pdtRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9));

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
		Cliente cli1 = new Cliente(null, "Dowglas Maia", "dowglasmaia@live.com", "003.653.658-87",
				TipoPessoa.PESSOA_FISICA, pe.encode("0123"));
		cli1.addPerfil(Perfil.CLIENTE);
		cli1.getTelefones().addAll(Arrays.asList("62-98888-8987", "89-95555-8745"));
		Endereco e1 = new Endereco(null, "Rua k15", "20-B", "Apt-250", "Centro", "78.658-890", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Ventos Leva", "220-B", "Apt-350", "Centro", "68.658-890", cli1, c3);
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		//****Cliente 02   -  cli2.addPerfil(Perfil.ADMIN);
		Cliente cli2 = new Cliente(null, "kayron", "dowglasmaia@gmail.com", "028.654.540-37", TipoPessoa.PESSOA_FISICA,
				pe.encode("0123"));
		cli2.addPerfil(Perfil.ADMIN);
		cli2.getTelefones().addAll(Arrays.asList("62-90215-6558", "89-3521-0254"));
		Endereco e3 = new Endereco(null, "Rua Bragona", "20-B", "Apt-2", "Centro", "78.658-890", cli2, c3);
		Endereco e4 = new Endereco(null, "Rua Sky Blue", "2-B", "Apt-3", "Copas", "68.658-890", cli1, c2);
		cli2.getEnderecos().addAll(Arrays.asList(e3, e4));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2,e3,e4));
		// ****

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// **Povoando o Pedido
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/09/2017 10:30"), cli2, e2);

		Pagamento pgt1 = new PgtoCartao(null, EstadoPgto.QUITADO, ped1, 6);
		ped1.setPagamento(pgt1);

		Pagamento pgt2 = new PgtoBoleto(null, EstadoPgto.PENDENTE, ped2, sdf.parse("19/10/2017 00:00"), null);
		ped2.setPagamento(pgt2);

		// associar os clientes ao pedidos do mesmo
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgt1, pgt2));

		// Itens de Pedido
		ItemPedido it1 = new ItemPedido(ped1, p1, 10.0, 2, p1.getPreco());
		ItemPedido it2 = new ItemPedido(ped1, p2, 200.0, 2, p2.getPreco());
		ItemPedido it3 = new ItemPedido(ped2, p3, 0.0, 5, p3.getPreco());

		ped1.getItens().addAll(Arrays.asList(it1, it2));
		ped2.getItens().addAll(Arrays.asList(it3));

		p1.getItens().addAll(Arrays.asList(it1));
		p2.getItens().addAll(Arrays.asList(it2));
		p3.getItens().addAll(Arrays.asList(it3));

		itemPedidoRepository.saveAll(Arrays.asList(it1, it2, it3));

	}

}