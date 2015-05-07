package com.rp.hd.repository.jpa;

import java.util.List;

import javax.ejb.Stateless;

import com.rp.hd.domain.Papel;

@Stateless
public class PapelRepository extends BaseRepository<Papel>{

	public PapelRepository() {
		super(Papel.class);
	}

	@Override
	public List<Papel> getTodos() {
		return em.createQuery("select o from Papel o where inativo = false order by o.nome asc", Papel.class).getResultList();
	}

}
