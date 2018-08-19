package com.maia.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Pedido;
import com.maia.cursomc.repositores.PedidoRepository;
import com.maia.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired // instanciando o Pedido Service
	private PedidoRepository repository;

	// metodo para BusarPor ID com SpringDataJPA
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
