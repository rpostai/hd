package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Colagem;

@Stateless
public class ColagemRepository extends BaseRepository<Colagem>{

	public ColagemRepository() {
		super(Colagem.class);
	}

}
