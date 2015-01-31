package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Renda;

@Stateless
public class RendaRepository extends BaseRepository<Renda>{

	public RendaRepository() {
		super(Renda.class);
	}

}
