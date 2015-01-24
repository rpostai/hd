package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;

import com.rp.hd.domain.utils.DateUtils;

@Entity
public class PrecoVigencia extends VigenciaBaseEntity {

	private BigDecimal valor;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
