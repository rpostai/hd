package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Papel;

@Stateless
public class PapelRepository extends BaseRepository<Papel>{

	public PapelRepository() {
		super(Papel.class);
	}

}
