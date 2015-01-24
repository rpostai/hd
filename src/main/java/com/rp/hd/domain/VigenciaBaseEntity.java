package com.rp.hd.domain;

import javax.persistence.Embedded;

public abstract class VigenciaBaseEntity extends BaseEntity {

	@Embedded
	private Vigencia vigencia;

	public Vigencia getVigencia() {
		return vigencia;
	}

	public void setVigencia(Vigencia vigencia) {
		this.vigencia = vigencia;
	}
	
}
