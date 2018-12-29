package com.maia.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Cidade;
import com.maia.cursomc.repositores.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;

	/*listar Cidades com base no ID do Estado da Mesma*/
	public List<Cidade> findByEstado(Integer estadoId) {
		return repository.findCidades(estadoId);
	}
}
