package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Strass;

@Stateless
public class StrassRepository extends BaseRepository<Strass> {

	public StrassRepository() {
		super(Strass.class);
	}

}
