package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

/**
 * @author rodrigo.postai
 *
 */
@Entity
public class Colagem extends BaseEntity {

	private String descricao;

	private BigDecimal markup;

	@ElementCollection
	@CollectionTable(name = "colagem_precos")
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

	public BigDecimal getCustoAtual() {
		return PrecoVigenciaService.getPrecoAtual(this.precos).getValor();
	}

	public BigDecimal getPrecoAtual() {
		return getCustoAtual().multiply(markup);
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

}
