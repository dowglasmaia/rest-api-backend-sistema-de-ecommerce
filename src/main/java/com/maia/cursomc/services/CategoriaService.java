package com.maia.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Categoria;
import com.maia.cursomc.repositores.CategoriaRepository;
import com.maia.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired // instanciando o Categoria Service
	private CategoriaRepository repository;

	// metodo para BusarPor ID com SpringDataJPA
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	// Inserir / Save
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	// update
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repository.save(obj);
	}

	// Delete
	public void delete(Integer id) {
		find(id); // Chama o Metodo Buscar por Id pra vereficar se o mesmo Existe
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é Possível Excluir Uma Categoria que Possui Produtos!");

		}

	}

	// Listar Todas as Categorias
	public List<Categoria> findAll() {
		return repository.findAll();
	}

}
