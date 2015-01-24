package com.rp.hd.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;

public class ImpressaoNome extends BaseEntity {

	private String descricao;

	@ElementCollection
	@CollectionTable(name = "impressao_nome_precos")
	private List<PrecoVigencia> precos = new ArrayList<>();

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<PrecoVigencia> getPrecos() {
		return precos;
	}

	public void addPreco(PrecoVigencia preco) {
		this.precos.add(preco);
	}

}
