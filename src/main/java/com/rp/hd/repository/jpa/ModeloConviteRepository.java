package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.ModeloConvite;

@Stateless
public class ModeloConviteRepository extends BaseRepository<ModeloConvite> {

	public ModeloConviteRepository() {
		super(ModeloConvite.class);
	}

}
