package com.rp.hd.repository.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.rp.hd.domain.atendimento.Orcamento;
import com.rp.hd.services.Dado;
import com.rp.hd.services.SolicitacaoOrcamento;

@Stateless
public class OrcamentoRepository extends BaseRepository<Orcamento> {

	public OrcamentoRepository() {
		super(Orcamento.class);
	}

	public List<SolicitacaoOrcamento> getOrcamentosPorAtendimento(Long atendimento) {
		TypedQuery<Orcamento> tq = em
				.createQuery(
						"select o from Orcamento o where o.atendimento.id = :atendimento",
						Orcamento.class);
		tq.setParameter("atendimento", atendimento);
		List<Orcamento> resultList = tq.getResultList();
		
		
		return resultList.stream().map(orcamento -> {
			
			SolicitacaoOrcamento s = new SolicitacaoOrcamento();
			s.setQuantidade(orcamento.getQuantidade());
			s.setQuantidadeStrass(orcamento.getQuantidadeStrass());
			
			s.setModelo(new Dado(orcamento.getModelo().getId(), orcamento.getModelo().getNome()));
			s.setPapelEnvelope(new Dado(orcamento.getPapelEnvelope().getId(), orcamento.getPapelEnvelope().getNome()));
			
			if (orcamento.getPapelInterno() != null) {
				s.setPapelInterno(new Dado(orcamento.getPapelInterno().getId(), orcamento.getPapelInterno().getNome()));	
			}
			
			if (orcamento.getImpressaoEnvelope() != null) {
				s.setImpressaoEnvelope(new Dado(orcamento.getImpressaoEnvelope().getId(), orcamento.getImpressaoEnvelope().getDescricao()));
			}
			
			if (orcamento.getImpressaoInterno() != null) {
				s.setImpressaoInterno(new Dado(orcamento.getImpressaoInterno().getId(), orcamento.getImpressaoInterno().getDescricao()));
			}
			
			if (orcamento.getFita() != null) {
				s.setFita(new Dado(orcamento.getFita().getId(), orcamento.getFita().toString()));
			}
			
			if (orcamento.getLaco() != null) {
				s.setLaco(new Dado(orcamento.getLaco().getId(), orcamento.getLaco().getDescricao()));
			}
			
			if (orcamento.getRenda() != null) {
				s.setRenda(new Dado(orcamento.getRenda().getId(), orcamento.getRenda().getDescricao()));
			}
			
			if (orcamento.getImpressaoNome() != null) {
				s.setImpressaoNome(new Dado(orcamento.getImpressaoNome().getId(), orcamento.getImpressaoNome().getDescricao()));
			}
			
			if (orcamento.getHotstamp() != null) {
				s.setHotstamp(new Dado(orcamento.getHotstamp().getId(), orcamento.getHotstamp().getDescricao()));
			}
			
			if (orcamento.getIma() != null) {
				s.setIma(new Dado(orcamento.getIma().getId(), Integer.toString(orcamento.getIma().getTamanho())));
			}
			
			if (orcamento.getStrass() != null) {
				s.setStrass(new Dado(orcamento.getStrass().getId(), orcamento.getStrass().getDescricao()));
			}
			
			if (orcamento.getSerigrafiaEnvelope() != null) {
				s.setSerigrafiaEnvelope(new Dado(orcamento.getSerigrafiaEnvelope().getId(), orcamento.getSerigrafiaEnvelope().getDescricao()));
			}
			
			if (orcamento.getSerigrafiaInterno() != null) {
				s.setSerigrafiaInterno(new Dado(orcamento.getSerigrafiaInterno().getId(), orcamento.getSerigrafiaInterno().getDescricao()));
			}
			
			s.setValor(orcamento.getPrecoCalculado());
			
			return s;
			
		}).collect(Collectors.toList());
		
	}

}
