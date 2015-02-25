package com.rp.hd.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="corte_envelope")
public class CorteEnvelope extends BaseEntity {

	private BigDecimal markup = BigDecimal.ONE;

	@ElementCollection
	@CollectionTable(name = "corte_envelope_precos", joinColumns=@JoinColumn(name="corte_envelope_id"))
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

	public BigDecimal getPrecoVenda(int quantidadeConvites) {
		return PrecoVigenciaService.getPrecoAtual(precos).getValor().divide(new BigDecimal(quantidadeConvites), 4, RoundingMode.HALF_UP).multiply(markup).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

}
