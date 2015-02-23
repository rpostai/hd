package com.rp.hd.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "complemento")
public class Complemento extends BaseEntity {

	private String descricao;

	private BigDecimal custo;

	private BigDecimal valorVenda;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	@Override
	public String toString() {
		return "Complemento [descricao=" + descricao + ", custo=" + custo
				+ ", valorVenda=" + valorVenda + "]";
	}

}
