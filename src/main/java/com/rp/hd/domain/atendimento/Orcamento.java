package com.rp.hd.domain.atendimento;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.rp.hd.domain.Papel;

@Entity
@Table(name = "orcamento")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Orcamento extends BaseConvite {

	private int quantidade;

	@Column(name = "preco_calculado")
	private BigDecimal precoCalculado;

	@Column(name = "preco_calculado_convites")
	private BigDecimal precoCalculadoConvites;

	@Column(name = "preco_calculado_items_pedido")
	private BigDecimal precoCalculadoItemsPedido;

	@Column(name = "preco_calculado_total_prazo")
	private BigDecimal precoCalculadoTotalPrazo;

	@Column(name = "preco_calculado_prazo")
	private BigDecimal precoCalculadoPrazo;

	@Column(name = "preco_calculado_convites_prazo")
	private BigDecimal precoCalculadoConvitesPrazo;

	@Column(name = "preco_calculado_items_pedido_prazo")
	private BigDecimal precoCalculadoItemsPedidoPrazo;

	@Column(name = "preco_calculado_total")
	private BigDecimal precoCalculadoTotal;

	@Column(name = "custo_unidade")
	private BigDecimal custoUnidade;

	@Column(name = "custo_outros")
	private BigDecimal custoOutros;

	@Column(name = "enviar_email")
	private Boolean enviarEmail = true;

	@Column(name = "tempo_estimado_unidade_em_segundos")
	private int tempoEstimadoPorUnidadeEmSegundos = 0;

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoCalculado() {
		return precoCalculado;
	}

	public void setPrecoCalculado(BigDecimal precoCalculado) {
		this.precoCalculado = precoCalculado;
	}

	public BigDecimal getPrecoCalculadoConvites() {
		return precoCalculadoConvites;
	}

	public void setPrecoCalculadoConvites(BigDecimal precoCalculadoConvites) {
		this.precoCalculadoConvites = precoCalculadoConvites;
	}

	public BigDecimal getPrecoCalculadoItemsPedido() {
		return precoCalculadoItemsPedido;
	}

	public void setPrecoCalculadoItemsPedido(
			BigDecimal precoCalculadoItemsPedido) {
		this.precoCalculadoItemsPedido = precoCalculadoItemsPedido;
	}

	public BigDecimal getPrecoCalculadoTotal() {
		return precoCalculadoTotal;
	}

	public void setPrecoCalculadoTotal(BigDecimal precoCalculadoTotal) {
		this.precoCalculadoTotal = precoCalculadoTotal;
	}

	public BigDecimal getCustoUnidade() {
		return custoUnidade;
	}

	public void setCustoUnidade(BigDecimal custoUnidade) {
		this.custoUnidade = custoUnidade;
	}

	public BigDecimal getCustoOutros() {
		return custoOutros;
	}

	public void setCustoOutros(BigDecimal custoOutros) {
		this.custoOutros = custoOutros;
	}

	public BigDecimal getPrecoCalculadoTotalPrazo() {
		return precoCalculadoTotalPrazo;
	}

	public void setPrecoCalculadoTotalPrazo(BigDecimal precoCalculadoTotalPrazo) {
		this.precoCalculadoTotalPrazo = precoCalculadoTotalPrazo;
	}

	public BigDecimal getPrecoCalculadoPrazo() {
		return precoCalculadoPrazo;
	}

	public void setPrecoCalculadoPrazo(BigDecimal precoCalculadoPrazo) {
		this.precoCalculadoPrazo = precoCalculadoPrazo;
	}

	public BigDecimal getPrecoCalculadoConvitesPrazo() {
		return precoCalculadoConvitesPrazo;
	}

	public void setPrecoCalculadoConvitesPrazo(
			BigDecimal precoCalculadoConvitesPrazo) {
		this.precoCalculadoConvitesPrazo = precoCalculadoConvitesPrazo;
	}

	public BigDecimal getPrecoCalculadoItemsPedidoPrazo() {
		return precoCalculadoItemsPedidoPrazo;
	}

	public void setPrecoCalculadoItemsPedidoPrazo(
			BigDecimal precoCalculadoItemsPedidoPrazo) {
		this.precoCalculadoItemsPedidoPrazo = precoCalculadoItemsPedidoPrazo;
	}

	public Boolean getEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(Boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public int getTempoEstimadoPorUnidadeEmSegundos() {
		return tempoEstimadoPorUnidadeEmSegundos;
	}

	public void setTempoEstimadoPorUnidadeEmSegundos(
			int tempoEstimadoPorUnidadeEmSegundos) {
		this.tempoEstimadoPorUnidadeEmSegundos = tempoEstimadoPorUnidadeEmSegundos;
	}

}
