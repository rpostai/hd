package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="hot_stamp")
public class HotStamp extends BaseEntity {

	private BigDecimal markup = BigDecimal.ONE;

	private String descricao;

	@ElementCollection
	@CollectionTable(name = "hot_stamp_precos", joinColumns=@JoinColumn(name="hot_stamp_id"))
	private List<PrecoVigencia> precos = new ArrayList<>();

	public List<PrecoVigencia> getPrecos() {
		return precos;
	}

	public void addPreco(PrecoVigencia preco) {
		this.precos.add(preco);
	}

	public BigDecimal getPrecoVenda() {
		return PrecoVigenciaService.getPrecoAtual(precos).getValor()
				.multiply(markup);
	}

	public String toString() {
		return "Aplicação Hot Stamping";
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
