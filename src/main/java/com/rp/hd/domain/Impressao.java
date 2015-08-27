package com.rp.hd.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Impressao extends BaseEntity {

	private String descricao;

	private BigDecimal markup;

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

	public BigDecimal getCustoAtual() {
		return PrecoVigenciaService.getPrecoAtual(this.precos).getValor();
	}

	public BigDecimal getPrecoVenda() {
		// Divide por 2 por que geralmente fazemos 2 impressões por folha
//		return getCustoAtual().multiply(
//				markup != null ? markup : BigDecimal.ONE).divide(new BigDecimal(2), 4, RoundingMode.HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		return getCustoAtual().multiply(
				markup != null ? markup : BigDecimal.ONE).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	@Override
	public String toString() {
		return descricao;
	}

}
