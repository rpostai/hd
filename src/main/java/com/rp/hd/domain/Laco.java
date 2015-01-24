package com.rp.hd.domain;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Laco extends BaseEntity {

	private String descricao;

	private int quantidadeFitaEmCentimetros;

	@ElementCollection
	@CollectionTable(name = "laco_maoobra_preco")
	private List<PrecoVigencia> custoMaoDeObra;

	public int getQuantidadeFitaEmCentimetros() {
		return quantidadeFitaEmCentimetros;
	}

	public void setQuantidadeFitaEmCentimetros(int quantidadeFitaEmCentimetros) {
		this.quantidadeFitaEmCentimetros = quantidadeFitaEmCentimetros;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<PrecoVigencia> getCustoMaoDeObra() {
		return custoMaoDeObra;
	}

	public void addCustoMaoDeObra(PrecoVigencia custoMaoDeObra) {
		this.custoMaoDeObra.add(custoMaoDeObra);
	}

}
