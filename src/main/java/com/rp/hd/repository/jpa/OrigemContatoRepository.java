package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.OrigemContato;

@Stateless
public class OrigemContatoRepository extends BaseRepository<OrigemContato> {

	public OrigemContatoRepository() {
		super(OrigemContato.class);
	}

}
