package com.rp.hd.services;

import java.io.Serializable;
import java.math.BigDecimal;

public class SolicitacaoOrcamento implements Serializable {

	private int quantidade;
	private Dado modelo;
	private Dado papelEnvelope;
	private Dado papelInterno;
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
				+ strass + ", ima=" + ima+"]";
	}

}
