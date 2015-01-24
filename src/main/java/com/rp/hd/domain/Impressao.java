package com.rp.hd.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Impressao extends BaseEntity {

	private String descricao;

	@ElementCollection
	@CollectionTable(name = "impressao_precos")
	private List<PrecoVigencia> precos = new ArrayList<>();

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<PrecoVigencia> getPrecos() {
		return Collections.unmodifiableList(precos);
	}

	public void addPreco(PrecoVigencia p) {
		this.precos.add(p);
	}

}
