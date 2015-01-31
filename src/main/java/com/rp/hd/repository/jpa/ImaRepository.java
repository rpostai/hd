package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Ima;

@Stateless
public class ImaRepository extends BaseRepository<Ima> {

	public ImaRepository() {
		super(Ima.class);
	}

}
