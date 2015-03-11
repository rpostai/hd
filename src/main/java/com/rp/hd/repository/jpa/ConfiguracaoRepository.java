package com.rp.hd.repository.jpa;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.rp.hd.domain.Configuracao;

@Stateless
public class ConfiguracaoRepository extends BaseRepository<Configuracao> {

	public ConfiguracaoRepository() {
		super(Configuracao.class);
	}
	
	public Optional<Configuracao> getConfiguracao(String chave) {
		TypedQuery<Configuracao> tq = em.createNamedQuery("Configuracao.PorChave", Configuracao.class);
		try {
			tq.setParameter("chave", chave);
			Configuracao c = tq.getSingleResult();
			return Optional.of(c);
		} catch (NoResultException e) {
			return Optional.empty();
		}
		
	}

}
