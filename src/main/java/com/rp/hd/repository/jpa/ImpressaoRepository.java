package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Impressao;

@Stateless
public class ImpressaoRepository extends BaseRepository<Impressao>{

	public ImpressaoRepository() {
		super(Impressao.class);
	}
	

}
