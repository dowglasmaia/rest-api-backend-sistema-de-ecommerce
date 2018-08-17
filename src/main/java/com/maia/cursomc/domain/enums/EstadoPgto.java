package com.maia.cursomc.domain.enums;

public enum EstadoPgto {
	PENDENTE(1, "Pendente"), 
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");

	private int cod;
	private String descricao;

	private EstadoPgto(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	//**Getters
	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}

	// metod para converter EstadoPgto para enum
	public static EstadoPgto toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (EstadoPgto x : EstadoPgto.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id Inválido: " + cod);

	}

}
