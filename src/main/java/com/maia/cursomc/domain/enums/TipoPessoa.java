package com.maia.cursomc.domain.enums;

public enum TipoPessoa {
	PESSOA_JURIDICA(1, "Pessoa Jurídica"), 
	PESSOA_FISICA(2, "Pessoa Fisíca");

	private int cod;
	private String descricao;

	private TipoPessoa(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	// metod para converter cliente para enum
	public static TipoPessoa toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoPessoa x : TipoPessoa.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id Inválido: " + cod);

	}

}
