package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Serigrafia;

@Stateless
public class SerigrafiaRepository extends BaseRepository<Serigrafia> {

	public SerigrafiaRepository() {
		super(Serigrafia.class);
	}

}
