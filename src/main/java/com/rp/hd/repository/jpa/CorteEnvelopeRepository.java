package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.CorteEnvelope;

@Stateless
public class CorteEnvelopeRepository extends BaseRepository<CorteEnvelope>{

	public CorteEnvelopeRepository() {
		super(CorteEnvelope.class);
	}

}
