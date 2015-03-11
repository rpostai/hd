package com.rp.hd.domain;

public enum Constants {
	TAXA_JUROS_MENSAL("TAXA_JUROS_MENSAL"), TAXA_ADMINISTRACAO_CARTAO_CREDITO(
			"TAXA_ADMINISTRACAO_CARTAO_CREDITO"), TAXA_ADMINISTRACAO_CARTAO_DEBITO(
			"TAXA_ADMINISTRACAO_CARTAO_DEBITO");

	private String descricao;

	private Constants(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
