package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="impressao_nome")
public class ImpressaoNome extends BaseEntity {

	private String descricao;

	private BigDecimal markup;

	@ElementCollection
	@CollectionTable(name = "impressao_nome_precos", joinColumns=@JoinColumn(name="impressao_nome_id"))
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

	public BigDecimal getMarkup() {
		return markup != null ? markup : BigDecimal.ONE;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public void addPreco(PrecoVigencia preco) {
		this.precos.add(preco);
	}

	public BigDecimal getPrecoVenda() {
		return PrecoVigenciaService.getPrecoAtual(precos).getValor()
				.multiply(getMarkup()).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public BigDecimal getPrecoCusto() {
		return PrecoVigenciaService.getPrecoAtual(precos).getValor()
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String toString() {
		return "Impressão do nome dos convidados " + descricao;
	}

}
