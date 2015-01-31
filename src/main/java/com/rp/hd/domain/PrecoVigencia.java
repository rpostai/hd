package com.rp.hd.domain;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class PrecoVigencia extends VigenciaBaseEntity {

	private BigDecimal valor;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
