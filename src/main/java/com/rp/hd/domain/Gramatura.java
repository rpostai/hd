package com.rp.hd.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Gramatura extends BaseEntity {

	@NotNull
	@Min(value = 0)
	@Max(value = 300)
	private int valor;

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
