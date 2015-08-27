package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.EventoParticipante;

@Stateless
public class EventoParticipanteRepository extends
		BaseRepository<EventoParticipante> {

	public EventoParticipanteRepository() {
		super(EventoParticipante.class);
	}

}
