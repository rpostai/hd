package com.rp.hd.domain;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrecoVigencia {

	private BigDecimal valor;

	@AttributeOverrides({
		@AttributeOverride(name="dataInicial", column=@Column(name="data_inicial")),
		@AttributeOverride(name="dataFinal", column=@Column(name="data_final")),
	})
	private Vigencia vigencia;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Vigencia getVigencia() {
		return vigencia;
	}

	public void setVigencia(Vigencia vigencia) {
		this.vigencia = vigencia;
	}

}
