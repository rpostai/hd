package com.rp.hd.repository.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.rp.hd.domain.Complemento;
import com.rp.hd.services.Dado;

@Stateless
public class ComplementoRepository extends BaseRepository<Complemento>{

	public ComplementoRepository() {
		super(Complemento.class);
	}
	
	public List<Dado> getComplementoAtivos() {
		List<Complemento> dados = getTodos();
		
		return dados.stream().map(c -> {
			Dado d = new Dado(c.getId(), c.getDescricao());
			d.setValor(c.getValorVenda());
			return d;
		}).collect(Collectors.toList());
	}

}
