package com.rp.hd.repository.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.rp.hd.domain.Evento;
import com.rp.hd.domain.atendimento.EventoConvite;
import com.rp.hd.services.Dado;
import com.rp.hd.services.SolicitacaoOrcamento;

@Stateless
public class EventoRepository extends BaseRepository<Evento> {

	public EventoRepository() {
		super(Evento.class);
	}

	public List<Evento> getTodosEventos() {
		TypedQuery<Evento> tq = em.createNamedQuery("Evento.TodosEventos",
				Evento.class);
		List<Evento> eventos = tq.getResultList();
		return eventos;
	}

	public List<SolicitacaoOrcamento> getTodosConvites(Evento evento) {
		TypedQuery<EventoConvite> tq = em.createNamedQuery(
				"Evento.TodosConvites", EventoConvite.class);
		tq.setParameter("evento", evento);
		List<EventoConvite> convites = tq.getResultList();

		return convites
				.parallelStream()
				.map(c -> {

					return converteConviteParaSolicitacaoOrcamento(c);
				}).collect(Collectors.toList());

	}

	public SolicitacaoOrcamento getDetalheConviteEvento(Evento evento,
			String codigoConvite) {
		TypedQuery<EventoConvite> tq = em.createNamedQuery(
				"Evento.ConviteDetalhe", EventoConvite.class);
		tq.setParameter("evento", evento);
		tq.setParameter("codigo", codigoConvite);
		EventoConvite c = tq.getSingleResult();

		return converteConviteParaSolicitacaoOrcamento(c);

	}

	private SolicitacaoOrcamento converteConviteParaSolicitacaoOrcamento(
			EventoConvite c) {
		SolicitacaoOrcamento s = new SolicitacaoOrcamento();
		s.setCodigoEvento(c.getCodigo());
		s.setPrecoEvento(c.getPrecoEvento());
		s.setQuantidadeStrass(c.getQuantidadeStrass());

		s.setModelo(new Dado(c.getModelo().getId(), c.getModelo().getNome()));
		s.setPapelEnvelope(new Dado(c.getPapelEnvelope().getId(), c
				.getPapelEnvelope().getNome()));

		if (c.getPapelInterno() != null) {
			s.setPapelInterno(new Dado(c.getPapelInterno().getId(), c
					.getPapelInterno().getNome()));
		}

		if (c.getImpressaoEnvelope() != null) {
			s.setImpressaoEnvelope(new Dado(c.getImpressaoEnvelope().getId(), c
					.getImpressaoEnvelope().getDescricao()));
		}

		if (c.getImpressaoInterno() != null) {
			s.setImpressaoInterno(new Dado(c.getImpressaoInterno().getId(), c
					.getImpressaoInterno().getDescricao()));
		}

		if (c.getFita() != null) {
			s.setFita(new Dado(c.getFita().getId(), c.getFita().toString()));
		}

		if (c.getLaco() != null) {
			s.setLaco(new Dado(c.getLaco().getId(), c.getLaco().getDescricao()));
		}

		if (c.getRenda() != null) {
			s.setRenda(new Dado(c.getRenda().getId(), c.getRenda()
					.getDescricao()));
		}

		if (c.getImpressaoNome() != null) {
			s.setImpressaoNome(new Dado(c.getImpressaoNome().getId(), c
					.getImpressaoNome().getDescricao()));
		}

		if (c.getHotstamp() != null) {
			s.setHotstamp(new Dado(c.getHotstamp().getId(), c.getHotstamp()
					.getDescricao()));
			s.getHotstamp().setValor(c.getHotstamp().getPrecoVenda());
		}

		if (c.getIma() != null) {
			s.setIma(new Dado(c.getIma().getId(), c.getIma().getDescricao()));
		}

		if (c.getStrass() != null) {
			s.setStrass(new Dado(c.getStrass().getId(), c.getStrass()
					.getDescricao()));
		}

		if (c.getSerigrafiaEnvelope() != null) {
			s.setSerigrafiaEnvelope(new Dado(c.getSerigrafiaEnvelope().getId(),
					c.getSerigrafiaEnvelope().getDescricao()));
		}

		if (c.getSerigrafiaInterno() != null) {
			s.setSerigrafiaInterno(new Dado(c.getSerigrafiaInterno().getId(), c
					.getSerigrafiaInterno().getDescricao()));
		}

		if (c.getCliche() != null) {
			s.setCliche(new Dado(c.getCliche().getId(), c.getCliche()
					.getDescricao()));
			s.getCliche().setValor(c.getCliche().getValorVenda());
		}

		s.setPrecoCalculado(c.getPrecoEvento());

		return s;
	}

}
