package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Cliche;

@Stateless
public class ClicheRepository extends BaseRepository<Cliche> {

	public ClicheRepository() {
		super(Cliche.class);
	}

}
