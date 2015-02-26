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
			sb.append(" and (upper(o.cliente1.nome) like :nome or upper(o.cliente2.nome) like :nome)");
		}
		if (data != null) {
			sb.append(" and (o.dataInicio >= :data1 and o.dataInicio <= :data2)");
		}
		
		sb.append(" order by o.dataInicio desc, o.cliente1.nome asc");

		TypedQuery<Atendimento> tq = em.createQuery(sb.toString(),
				Atendimento.class);
		
		if (StringUtils.isNotBlank(numero)) {
			tq.setParameter("numero", numero);
		}
		if (StringUtils.isNotBlank(nome)) {
			tq.setParameter("nome", "%"+StringUtils.upperCase(nome)+"%");
		}
		if (data != null) {
			
			Calendar c = Calendar.getInstance();
			c.setTime(data.getTime());
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			
			Calendar c1 = Calendar.getInstance();
			c1.setTime(data.getTime());
			c1.set(Calendar.HOUR, 23);
			c1.set(Calendar.MINUTE, 59);
			c1.set(Calendar.SECOND, 59);
			
			tq.setParameter("data1", c.getTime(), TemporalType.TIMESTAMP);
			tq.setParameter("data2", c1.getTime(), TemporalType.TIMESTAMP);
		}
		return tq.getResultList();
	}
}
