package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Renda extends BaseEntity {

	private String descricao;

	private BigDecimal markup = BigDecimal.ONE;

	@ElementCollection
	@CollectionTable(name = "renda_precos")
	public List<PrecoVigencia> precos = new ArrayList<>();

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

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}
	
	public BigDecimal getPrecoCusto(ModeloConvite modelo) {
		return PrecoVigenciaService
				.getPrecoAtual(precos)
				.getValor()
				.multiply(
						new BigDecimal(modelo.getQuantidadeRendaEmCentimetros()))
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getPrecoVenda(ModeloConvite modelo) {
		return PrecoVigenciaService
				.getPrecoAtual(precos)
				.getValor()
				.multiply(
						new BigDecimal(modelo.getQuantidadeRendaEmCentimetros()))
				.multiply(markup).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String toString() {
		return "Aplicação de renda";
	}

}
