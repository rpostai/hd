package com.rp.hd.repository.jpa;

import java.util.ArrayList;
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

	private List<SolicitacaoOrcamento> getOrcamentosPorAtendimento(Long atendimento, Boolean somenteEnviarEmail) {
		StringBuilder sb = new StringBuilder();
		sb.append("select o from Orcamento o ");
		sb.append("  join o.atendimento    a");
		sb.append(" where (a.cliente1 in  ");
		sb.append("  (select cliente1 from Atendimento at1 where at1.id = :atendimento) ");
		sb.append("   or a.cliente2 in ");
		sb.append("  (select cliente2 from Atendimento at2 where at2.id = :atendimento)");
		sb.append(") ");
		if (somenteEnviarEmail != null) {
			sb.append(" and o.enviarEmail = :enviarEmail ");
		}
		sb.append("order by a.id desc");
		
//		TypedQuery<Orcamento> tq = em
//				.createQuery(
//						"select o from Orcamento o left join o.cliente1 c1 left join o.cliente2 c2 where o.atendimento.id = :atendimento",
//						Orcamento.class);
		
		TypedQuery<Orcamento> tq = em.createQuery(sb.toString(),Orcamento.class);
		
		tq.setParameter("atendimento", atendimento);
		if (somenteEnviarEmail != null) {
			tq.setParameter("enviarEmail", somenteEnviarEmail);
		}
		List<Orcamento> resultList = tq.getResultList();

		return resultList.stream().map(orcamento -> {
			
			SolicitacaoOrcamento s = new SolicitacaoOrcamento();
			s.setId(orcamento.getId());
			s.setEnviarEmail(orcamento.getEnviarEmail());
			s.setDataAtendimento(orcamento.getAtendimento().getDataInicio().getTime());
			s.setQuantidade(orcamento.getQuantidade());
			s.setQuantidadeStrass(orcamento.getQuantidadeStrass());
			
			Dado modelo = new Dado(orcamento.getModelo().getId(), orcamento.getModelo().getNome());
			modelo.setCodigo(orcamento.getModelo().getCodigo());
			s.setModelo(modelo);
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
				s.getHotstamp().setValor(orcamento.getHotstamp().getPrecoVenda());
			}
			
			if (orcamento.getIma() != null) {
				s.setIma(new Dado(orcamento.getIma().getId(), orcamento.getIma().getDescricao()));
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
			
			if (orcamento.getCliche() != null) {
				s.setCliche(new Dado(orcamento.getCliche().getId(), orcamento.getCliche().getDescricao()));
				s.getCliche().setValor(orcamento.getCliche().getValorVenda());
			}
			
			if (orcamento.getAcoplamentoEnvelope() != null) {
				s.setAcoplamentoEnvelope(true);
			} else {
				s.setAcoplamentoEnvelope(false);
			}
			
			if (orcamento.getAcoplamentoInterno() != null) {
				s.setAcoplamentoInterno(true);
			} else {
				s.setAcoplamentoInterno(false);
			}
			
			if (orcamento.getCorteInternoAlmofadado() != null) {
				s.setCorteInternoAlmofadado(true);
			} else {
				s.setCorteInternoAlmofadado(false);
			}
			
			s.setPrecoCalculado(orcamento.getPrecoCalculado());
			s.setPrecoCalculadoPrazo(orcamento.getPrecoCalculadoPrazo());
			
			s.setPrecoCalculadoItemsPedido(orcamento.getPrecoCalculadoItemsPedido());
			s.setPrecoCalculadoItemsPedidoPrazo(orcamento.getPrecoCalculadoItemsPedidoPrazo());
			
			s.setPrecoCalculadoTotal(orcamento.getPrecoCalculadoTotal());
			s.setPrecoCalculadoTotalPrazo(orcamento.getPrecoCalculadoTotalPrazo());
			
			s.setPrecoCalculadoConvites(orcamento.getPrecoCalculadoConvites());
			s.setPrecoCalculadoConvitesPrazo(orcamento.getPrecoCalculadoConvitesPrazo());
			
			
			return s;
			
		}).collect(Collectors.toList());
		
	}
	
	public List<SolicitacaoOrcamento> getOrcamentosPorAtendimento(Long atendimento) {
		return getOrcamentosPorAtendimento(atendimento, null);
	}
	
	public List<SolicitacaoOrcamento> getOrcamentosPorAtendimentoParaEnvioEmail(Long atendimento) {
		return getOrcamentosPorAtendimento(atendimento, true);
	}

}
