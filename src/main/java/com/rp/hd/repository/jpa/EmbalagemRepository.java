package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Embalagem;

@Stateless
public class EmbalagemRepository extends BaseRepository<Embalagem>{

	public EmbalagemRepository() {
		super(Embalagem.class);
	}

}
