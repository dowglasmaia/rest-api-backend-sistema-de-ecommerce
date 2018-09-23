package com.maia.cursomc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Cliente;
import com.maia.cursomc.domain.ItemPedido;
import com.maia.cursomc.domain.Pedido;
import com.maia.cursomc.domain.PgtoBoleto;
import com.maia.cursomc.domain.enums.EstadoPgto;
import com.maia.cursomc.domain.enums.Perfil;
import com.maia.cursomc.domain.security.UserSS;
import com.maia.cursomc.repositores.ItemPedidoRepository;
import com.maia.cursomc.repositores.PagamentoRepository;
import com.maia.cursomc.repositores.PedidoRepository;
import com.maia.cursomc.services.email.EmailService;
import com.maia.cursomc.services.exception.AuthorizationException;
import com.maia.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired // Injetando as instancias para o Pedido Service
	private PedidoRepository repository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	// metodo para BusarPor ID com SpringDataJPA
	public Pedido find(Integer id) {	
		Optional<Pedido> obj = repository.findById(id);		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	// inserir Pedido
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null); // garantindo que é um pedido novo
		obj.setDtaPedido(new Date()); // gera a data atual do sistema
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstadoPgto(EstadoPgto.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PgtoBoleto) {
			PgtoBoleto pgtBoleto = (PgtoBoleto) obj.getPagamento();
			boletoService.preencherPgtoBoleto(pgtBoleto, obj.getDtaPedido());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido ip : obj.getItens()) {
			ip.setDescont(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		// emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}

	// Listando Pedidos Paginado! por Clinte Logado
	public Page<Pedido> findPage(Integer page, Integer linesPage, String orderBy, String direction) {
		// pegando o usuario logado e autenticado
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso Negado!");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repository.findByCliente(cliente, pageRequest);

	}

	// Listar Todos os Pedidos
	public List<Pedido> findAll() {
		return repository.findAll();
	}

}
