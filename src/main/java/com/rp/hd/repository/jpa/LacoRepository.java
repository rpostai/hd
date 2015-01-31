package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Laco;

@Stateless
public class LacoRepository extends BaseRepository<Laco>{

	public LacoRepository() {
		super(Laco.class);
	}

}
