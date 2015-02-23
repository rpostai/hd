package com.rp.hd.domain.atendimento;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rp.hd.domain.BaseEntity;
import com.rp.hd.domain.Complemento;

@Entity
@Table(name = "orcamento_complemento")
public class OrcamentoComplemento extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "atendimento_id")
	private Atendimento atendimento;

	@ManyToOne
	@JoinColumn(name = "complemento_id")
	private Complemento complemento;

	private int quantidade;

	private BigDecimal valor;

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public Complemento getComplemento() {
		return complemento;
	}

	public void setComplemento(Complemento complemento) {
		this.complemento = complemento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
