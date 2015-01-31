package com.rp.hd.services;

import java.math.BigDecimal;

public class RelatorioPreco {

	private int quantidade;

	private BigDecimal envelope;

	private BigDecimal interno;

	private BigDecimal impressaoInterno;

	private BigDecimal impressaoEnvelope;

	private BigDecimal hotStamp;

	private BigDecimal serigrafia;

	private BigDecimal laco;

	private BigDecimal strass;

	private BigDecimal embalagem;

	private BigDecimal precoFinal;

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getEnvelope() {
		return envelope;
	}

	public void setEnvelope(BigDecimal envelope) {
		this.envelope = envelope;
	}

	public BigDecimal getInterno() {
		return interno;
	}

	public void setInterno(BigDecimal interno) {
		this.interno = interno;
	}

	public BigDecimal getImpressaoInterno() {
		return impressaoInterno;
	}

	public void setImpressaoInterno(BigDecimal impressaoInterno) {
		this.impressaoInterno = impressaoInterno;
	}

	public BigDecimal getImpressaoEnvelope() {
		return impressaoEnvelope;
	}

	public void setImpressaoEnvelope(BigDecimal impressaoEnvelope) {
		this.impressaoEnvelope = impressaoEnvelope;
	}

	public BigDecimal getHotStamp() {
		return hotStamp;
	}

	public void setHotStamp(BigDecimal hotStamp) {
		this.hotStamp = hotStamp;
	}

	public BigDecimal getSerigrafia() {
		return serigrafia;
	}

	public void setSerigrafia(BigDecimal serigrafia) {
		this.serigrafia = serigrafia;
	}

	public BigDecimal getLaco() {
		return laco;
	}

	public void setLaco(BigDecimal laco) {
		this.laco = laco;
	}

	public BigDecimal getStrass() {
		return strass;
	}

	public void setStrass(BigDecimal strass) {
		this.strass = strass;
	}

	public BigDecimal getEmbalagem() {
		return embalagem;
	}

	public void setEmbalagem(BigDecimal embalagem) {
		this.embalagem = embalagem;
	}

	public BigDecimal getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(BigDecimal precoFinal) {
		this.precoFinal = precoFinal;
	}

}
