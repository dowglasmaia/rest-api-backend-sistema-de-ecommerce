package com.maia.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Estado;
import com.maia.cursomc.repositores.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repository;

	public List<Estado>findAll(){
		return repository.findByOrderByNome();
	}
	
}
