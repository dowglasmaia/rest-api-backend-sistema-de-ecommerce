package com.maia.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Cliente;
import com.maia.cursomc.dto.ClienteDTO;
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

	// Inserir / Save
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	// update
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}
	//metodo auxilar para atualizar um novo cliente
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	// Delete
	public void delete(Integer id) {
		find(id); // Chama o Metodo Buscar por Id pra vereficar se o mesmo Existe
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é Possível Excluir Este Cliente!");
		}
	}

	// Listar Todas as Clientes
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	// Listando Clientes por Paginação
	public Page<Cliente> findPage(Integer page, Integer linesPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	// Metodo Auxiliar para Instaciar Uma Cliente Apartir de um DTO
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}

}
