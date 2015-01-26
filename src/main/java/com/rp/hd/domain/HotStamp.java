package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class HotStamp extends BaseEntity {
	
	private BigDecimal markup = BigDecimal.ONE;

	@ElementCollection
	@CollectionTable(name = "hot_stamp_precos")
	private List<PrecoVigencia> precos = new ArrayList<>();

	public List<PrecoVigencia> getPrecos() {
		return precos;
	}

	public void addPreco(PrecoVigencia preco) {
		this.precos.add(preco);
	}
	
	public BigDecimal getPrecoVenda() {
		return PrecoVigenciaService.getPrecoAtual(precos).getValor().multiply(markup);
	}
	
	public String toString() {
		return "Aplicação Hot Stamping";
	}

}
