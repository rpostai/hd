package com.rp.hd.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rp.hd.domain.atendimento.Orcamento;
import com.rp.hd.domain.atendimento.OrcamentoComplemento;

@Entity
@Table(name="pedido_item")
public class PedidoItem implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@OneToOne
	@JoinColumn(name="orcamento_id")
	private Orcamento orcamento;

	@OneToOne
	@JoinColumn(name="complemento_id")
	private OrcamentoComplemento complemento;

	@JoinColumn(name="valor_unidade_final")
	private BigDecimal valorUnidadeFinal;

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public OrcamentoComplemento getComplemento() {
		return complemento;
	}

	public void setComplemento(OrcamentoComplemento complemento) {
		this.complemento = complemento;
	}

	public BigDecimal getValorUnidadeFinal() {
		return valorUnidadeFinal;
	}

	public void setValorUnidadeFinal(BigDecimal valorUnidadeFinal) {
		this.valorUnidadeFinal = valorUnidadeFinal;
	}

}
