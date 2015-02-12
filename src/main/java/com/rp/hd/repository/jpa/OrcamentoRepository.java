package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.atendimento.Orcamento;

@Stateless
public class OrcamentoRepository extends BaseRepository<Orcamento> {

	public OrcamentoRepository() {
		super(Orcamento.class);
	}

}
