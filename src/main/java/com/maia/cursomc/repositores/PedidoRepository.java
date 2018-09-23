package com.maia.cursomc.repositores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.maia.cursomc.domain.Cliente;
import com.maia.cursomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	//Consulta de Pedido por Cliente - paginada
	@Transactional(readOnly = true)
	Page<Pedido>findByCliente(Cliente cliente, Pageable pageResquest);

	
}
