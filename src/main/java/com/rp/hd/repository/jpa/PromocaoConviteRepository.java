package com.rp.hd.repository.jpa;

import java.util.List;

import javax.ejb.Stateless;

import com.rp.hd.domain.atendimento.PromocaoConvite;

@Stateless
public class PromocaoConviteRepository extends BaseRepository<PromocaoConvite> {

	public PromocaoConviteRepository() {
		super(PromocaoConvite.class);
	}

	public List<PromocaoConvite> getPromocoesAtivas() {
		return em.createNamedQuery("PromocaoConvite.PromocoesAtivas",
				PromocaoConvite.class).getResultList();
	}

}
