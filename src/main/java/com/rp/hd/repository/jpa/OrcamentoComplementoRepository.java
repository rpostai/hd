package com.rp.hd.repository.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.rp.hd.domain.atendimento.OrcamentoComplemento;

@Stateless
public class OrcamentoComplementoRepository extends
		BaseRepository<OrcamentoComplemento> {

	public OrcamentoComplementoRepository() {
		super(OrcamentoComplemento.class);
	}

	public List<OrcamentoComplemento> getComplementos(Long atendimento) {
		TypedQuery<OrcamentoComplemento> tq = em
				.createQuery(
						"select o from OrcamentoComplemento o where o.atendimento.id = :atendimento",
						OrcamentoComplemento.class);
		tq.setParameter("atendimento", atendimento);
		return tq.getResultList();
	}

}
