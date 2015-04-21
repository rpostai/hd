package com.rp.hd.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "acoplamento")
public class Acoplamento extends BaseEntity {

	@Column(name="markup")
	private BigDecimal markup;
	
	@ElementCollection
	@CollectionTable(name = "acoplamento_precos", joinColumns = @JoinColumn(name = "acoplamento_id"))
	private List<PrecoVigencia> precos = new ArrayList<>();

	public void addPreco(PrecoVigencia p) {
		this.precos.add(p);
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public BigDecimal getCusto(int quantidadeConvites) {
		return PrecoVigenciaService
				.getPrecoAtual(precos)
				.getValor()
				.divide(new BigDecimal(quantidadeConvites), 4,
						RoundingMode.HALF_UP)
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getPrecoVenda(int quantidadeConvites) {
		return PrecoVigenciaService
				.getPrecoAtual(precos)
				.getValor()
				.divide(new BigDecimal(quantidadeConvites), 4,
						RoundingMode.HALF_UP).multiply(markup)
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String toString() {
		return "Serviço de acoplamento";
	}
	
	
}
