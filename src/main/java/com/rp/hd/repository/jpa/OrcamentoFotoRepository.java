package com.rp.hd.repository.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
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
	
	public void removerFotosPorAtendimento(Long id) {
		Query tq = em.createNamedQuery("OrcamentoFotos.RemoverPorAtendimento");
		tq.setParameter("id", id);
		tq.executeUpdate();
	}
}
