package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Laco extends BaseEntity {

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "qtd_centimetros_fita")
	private int quantidadeFitaEmCentimetros;

	private BigDecimal markup;

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

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public void setCustoMaoDeObra(List<PrecoVigencia> custoMaoDeObra) {
		this.custoMaoDeObra = custoMaoDeObra;
	}

}
