package com.rp.hd.repository.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.rp.hd.domain.OrcamentoFoto;

@Stateless
public class OrcamentoFotoRepository extends BaseRepository<OrcamentoFoto> {

	public OrcamentoFotoRepository() {
		super(OrcamentoFoto.class);
	}
	
	public List<OrcamentoFoto> getFotosPorAtendimento(Long id) {
		TypedQuery<OrcamentoFoto> tq = em.createNamedQuery("OrcamentoFotos.PorAtendimento", OrcamentoFoto.class);
		tq.setParameter("id", id);
		return tq.getResultList();
	}
}
