package com.maia.cursomc.domain;

import com.maia.cursomc.domain.enums.EstadoPgto;

public class PgtoCartao extends Pagamento {
	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;

	// Construtores
	public PgtoCartao() {
	}

	public PgtoCartao(Integer id, EstadoPgto estadoPgto, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estadoPgto, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	// **Getters e Setters
	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

}
