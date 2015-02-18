package com.rp.hd.repository.jpa.atendimento;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;

import com.rp.hd.domain.atendimento.Atendimento;
import com.rp.hd.repository.jpa.BaseRepository;

@Stateless
public class AtendimentoRepository extends BaseRepository<Atendimento> {

	public AtendimentoRepository() {
		super(Atendimento.class);
	}

	public List<Atendimento> consultar(String numero, String nome, Calendar data) {
		StringBuilder sb = new StringBuilder();
		sb.append("select o from Atendimento o where 1 = 1 ");
		if (StringUtils.isNotBlank(numero)) {
			sb.append(" and o.numero = :numero");
		}
		if (StringUtils.isNotBlank(nome)) {
			sb.append(" and (o.cliente1.nome like :nome or o.cliente2.nome like :nome)");
		}
		if (data != null) {
			sb.append(" and (:data = date_trunc('day',o).dataInicio)");
		}

		TypedQuery<Atendimento> tq = em.createQuery(sb.toString(),
				Atendimento.class);
		
		if (StringUtils.isNotBlank(numero)) {
			tq.setParameter("numero", numero);
		}
		if (StringUtils.isNotBlank(nome)) {
			tq.setParameter("nome", "%"+nome+"%");
		}
		if (data != null) {
			tq.setParameter("data", data, TemporalType.DATE);
		}
		return tq.getResultList();
	}

}
