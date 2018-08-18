package com.maia.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.maia.cursomc.domain.enums.EstadoPgto;

@Entity
public class PgtoBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;

	private Date dtaVencimento;
	private Date dtaPagamento;
	

	public PgtoBoleto() {
	}

	public PgtoBoleto(Integer id, EstadoPgto estadoPgto, Pedido pedido, Date dtaVencimento, Date dtaPagamento) {
		super(id, estadoPgto, pedido);
		this.dtaVencimento = dtaVencimento;
		this.dtaPagamento = dtaPagamento;
	}

	//**Getters e Setters
	public Date getDtaVencimento() {
		return dtaVencimento;
	}

	public void setDtaVencimento(Date dtaVencimento) {
		this.dtaVencimento = dtaVencimento;
	}

	public Date getDtaPagamento() {
		return dtaPagamento;
	}

	public void setDtaPagamento(Date dtaPagamento) {
		this.dtaPagamento = dtaPagamento;
	}
	
	
	

}
