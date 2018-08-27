package com.maia.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Categoria;
import com.maia.cursomc.domain.Produto;
import com.maia.cursomc.repositores.CategoriaRepository;
import com.maia.cursomc.repositores.ProdutoRepository;
import com.maia.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired // instanciando o Produto Service
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository catRepository;

	// metodo para BusarPor ID com SpringDataJPA
	public Produto find(Integer id) {
		Optional<Produto> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	// Buscar de Produtos com JPQL...
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPage, String orderBy,
			String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		List<Categoria>categorias = catRepository.findAllById(ids);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
