package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Acoplamento;

@Stateless
public class AcoplamentoRepository extends BaseRepository<Acoplamento> {

	public AcoplamentoRepository() {
		super(Acoplamento.class);
	}

}
