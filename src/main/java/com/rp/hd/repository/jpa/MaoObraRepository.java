package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.MaoObra;

@Stateless
public class MaoObraRepository extends BaseRepository<MaoObra> {

	public MaoObraRepository() {
		super(MaoObra.class);
	}

}
