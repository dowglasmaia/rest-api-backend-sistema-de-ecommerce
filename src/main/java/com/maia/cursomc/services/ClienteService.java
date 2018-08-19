package com.maia.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Cliente;
import com.maia.cursomc.repositores.ClienteRepository;
import com.maia.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired // instanciando o Cliente Service
	private ClienteRepository repository;

	// metodo para BusarPor ID com SpringDataJPA
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

}
