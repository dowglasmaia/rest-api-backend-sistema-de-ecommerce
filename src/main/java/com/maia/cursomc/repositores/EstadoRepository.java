package com.maia.cursomc.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.maia.cursomc.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	
	/*Buscar Todos os Estados Ordenados por Nome*/
	@Transactional(readOnly = true)
	public List<Estado>findByOrderByNome();

}
