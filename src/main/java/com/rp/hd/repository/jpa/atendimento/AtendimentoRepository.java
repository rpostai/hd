package com.rp.hd.repository.jpa.atendimento;

import javax.ejb.Stateless;

import com.rp.hd.domain.atendimento.Atendimento;
import com.rp.hd.repository.jpa.BaseRepository;

@Stateless
public class AtendimentoRepository extends BaseRepository<Atendimento> {

	public AtendimentoRepository() {
		super(Atendimento.class);
	}

}
