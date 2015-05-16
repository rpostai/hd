package com.rp.hd.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolicitacaoOrcamento implements Serializable {

	private Long id;
	private String codigoEvento;
	private BigDecimal precoEvento;
	private Date dataAtendimento;
	private int quantidade;
	private Dado modelo;
	private Dado papelEnvelope;
	private Dado papelInterno;
	private Dado papelRevestimentoInterno;
	private Dado impressaoEnvelope;
	private Dado impressaoInterno;
	private Dado fita;
	private Dado laco;
	private Dado renda;
	private Dado impressaoNome;
	private Dado serigrafiaInterno;
	private Dado serigrafiaEnvelope;
	private Dado hotstamp;
	private Dado strass;
	private int quantidadeStrass;
	private Dado ima;
	private Dado cliche;
	private boolean corteInternoAlmofadado;
	private boolean acoplamentoEnvelope;
	private boolean acoplamentoInterno;

	private BigDecimal precoCalculado;
	private BigDecimal precoCalculadoConvites;
	private BigDecimal precoCalculadoItemsPedido;
	private BigDecimal precoCalculadoTotal;

	private BigDecimal precoCalculadoPrazo;
	private BigDecimal precoCalculadoConvitesPrazo;
	private BigDecimal precoCalculadoItemsPedidoPrazo;
	private BigDecimal precoCalculadoTotalPrazo;

	private Boolean enviarEmail;

	private List<String> fotos = new ArrayList<String>();

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Dado getModelo() {
		return modelo;
	}

	public void setModelo(Dado modelo) {
		this.modelo = modelo;
	}

	public Dado getPapelEnvelope() {
		return papelEnvelope;
	}

	public void setPapelEnvelope(Dado papelEnvelope) {
		this.papelEnvelope = papelEnvelope;
	}

	public Dado getPapelInterno() {
		return papelInterno;
	}

	public void setPapelInterno(Dado papelInterno) {
		this.papelInterno = papelInterno;
	}

	public Dado getImpressaoEnvelope() {
		return impressaoEnvelope;
	}

	public void setImpressaoEnvelope(Dado impressaoEnvelope) {
		this.impressaoEnvelope = impressaoEnvelope;
	}

	public Dado getImpressaoInterno() {
		return impressaoInterno;
	}

	public void setImpressaoInterno(Dado impressaoInterno) {
		this.impressaoInterno = impressaoInterno;
	}

	public Dado getFita() {
		return fita;
	}

	public void setFita(Dado fita) {
		this.fita = fita;
	}

	public Dado getLaco() {
		return laco;
	}

	public void setLaco(Dado laco) {
		this.laco = laco;
	}

	public Dado getRenda() {
		return renda;
	}

	public void setRenda(Dado renda) {
		this.renda = renda;
	}

	public Dado getImpressaoNome() {
		return impressaoNome;
	}

	public void setImpressaoNome(Dado impressaoNome) {
		this.impressaoNome = impressaoNome;
	}

	public Dado getSerigrafiaInterno() {
		return serigrafiaInterno;
	}

	public void setSerigrafiaInterno(Dado serigrafiaInterno) {
		this.serigrafiaInterno = serigrafiaInterno;
	}

	public Dado getSerigrafiaEnvelope() {
		return serigrafiaEnvelope;
	}

	public void setSerigrafiaEnvelope(Dado serigrafiaEnvelope) {
		this.serigrafiaEnvelope = serigrafiaEnvelope;
	}

	public Dado getHotstamp() {
		return hotstamp;
	}

	public void setHotstamp(Dado hotstamp) {
		this.hotstamp = hotstamp;
	}

	public Dado getStrass() {
		return strass;
	}

	public void setStrass(Dado strass) {
		this.strass = strass;
	}

	public Dado getIma() {
		return ima;
	}

	public void setIma(Dado ima) {
		this.ima = ima;
	}

	public int getQuantidadeStrass() {
		return quantidadeStrass;
	}

	public void setQuantidadeStrass(int quantidadeStrass) {
		this.quantidadeStrass = quantidadeStrass;
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

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public Dado getCliche() {
		return cliche;
	}

	public void setCliche(Dado cliche) {
		this.cliche = cliche;
	}

	public void addFoto(String foto) {
		this.fotos.add(foto);
	}

	public List<String> getFotos() {
		return fotos;
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

	public BigDecimal getPrecoCalculadoTotalPrazo() {
		return precoCalculadoTotalPrazo;
	}

	public void setPrecoCalculadoTotalPrazo(BigDecimal precoCalculadoTotalPrazo) {
		this.precoCalculadoTotalPrazo = precoCalculadoTotalPrazo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(Boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public boolean isCorteInternoAlmofadado() {
		return corteInternoAlmofadado;
	}

	public void setCorteInternoAlmofadado(boolean corteInternoAlmofadado) {
		this.corteInternoAlmofadado = corteInternoAlmofadado;
	}

	public boolean isAcoplamentoEnvelope() {
		return acoplamentoEnvelope;
	}

	public void setAcoplamentoEnvelope(boolean acoplamentoEnvelope) {
		this.acoplamentoEnvelope = acoplamentoEnvelope;
	}

	public boolean isAcoplamentoInterno() {
		return acoplamentoInterno;
	}

	public void setAcoplamentoInterno(boolean acoplamentoInterno) {
		this.acoplamentoInterno = acoplamentoInterno;
	}

	public String getCodigoEvento() {
		return codigoEvento;
	}

	public void setCodigoEvento(String codigoEvento) {
		this.codigoEvento = codigoEvento;
	}

	public BigDecimal getPrecoEvento() {
		return precoEvento;
	}

	public void setPrecoEvento(BigDecimal precoEvento) {
		this.precoEvento = precoEvento;
	}

	@Override
	public String toString() {
		return "SolicitacaoOrcamento [quantidade=" + quantidade + ", modelo="
				+ modelo + ", papelEnvelope=" + papelEnvelope
				+ ", papelInterno=" + papelInterno + ", impressaoEnvelope="
				+ impressaoEnvelope + ", impressaoInterno=" + impressaoInterno
				+ ", fita=" + fita + ", laco=" + laco + ", renda=" + renda
				+ ", impressaoNome=" + impressaoNome + ", serigrafiaInterno="
				+ serigrafiaInterno + ", serigrafiaEnvelope="
				+ serigrafiaEnvelope + ", hotstamp=" + hotstamp + ", strass="
				+ strass + ", ima=" + ima + "]";
	}

	public Dado getPapelRevestimentoInterno() {
		return papelRevestimentoInterno;
	}

	public void setPapelRevestimentoInterno(Dado papelRevestimentoInterno) {
		this.papelRevestimentoInterno = papelRevestimentoInterno;
	}

}
