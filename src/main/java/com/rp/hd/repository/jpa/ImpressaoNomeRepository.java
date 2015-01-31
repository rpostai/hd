package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.ImpressaoNome;

@Stateless
public class ImpressaoNomeRepository extends BaseRepository<ImpressaoNome> {

	public ImpressaoNomeRepository() {
		super(ImpressaoNome.class);
	}

}
