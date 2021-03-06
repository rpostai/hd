package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Ima extends BaseEntity {

	private int tamanho;

	private String descricao;

	private BigDecimal markup;

	@ElementCollection
	@CollectionTable(name = "ima_precos")
	private List<PrecoVigencia> precos = new ArrayList<>();

	public int getTamanho() {
		return tamanho;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public List<PrecoVigencia> getPrecos() {
		return precos;
	}

	public void addPreco(PrecoVigencia preco) {
		this.precos.add(preco);
	}

	public BigDecimal getCusto() {
		return PrecoVigenciaService.getPrecoAtual(precos).getValor()
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getPrecoVenda() {
		return PrecoVigenciaService.getPrecoAtual(precos).getValor()
				.multiply(markup).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

}
