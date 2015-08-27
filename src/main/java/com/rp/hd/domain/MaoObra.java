package com.rp.hd.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mao_obra")
public class MaoObra implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "tipo_mao_obra", length = 100)
	@Enumerated(EnumType.STRING)
	private TipoMaoObra tipoMaoObra;

	@Column(name = "custo")
	private BigDecimal custo;

	@Column(name = "markup")
	private BigDecimal markup;

	@Column(name = "tempo_medio_por_unidade")
	private int tempoMedioPorUnidade;

	public TipoMaoObra getTipoMaoObra() {
		return tipoMaoObra;
	}

	public void setTipoMaoObra(TipoMaoObra tipoMaoObra) {
		this.tipoMaoObra = tipoMaoObra;
	}

	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public BigDecimal getValorVenda() {
		return custo.multiply(markup);
	}

	public int getTempoMedioPorUnidade() {
		return tempoMedioPorUnidade;
	}

	public void setTempoMedioPorUnidade(int tempoMedioPorUnidade) {
		this.tempoMedioPorUnidade = tempoMedioPorUnidade;
	}

}
