package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Fita;

@Stateless
public class FitaRepository extends BaseRepository<Fita> {

	public FitaRepository() {
		super(Fita.class);
	}

}
