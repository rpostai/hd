package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Papel extends BaseEntity {

	private String nome;

	@ManyToOne
	@JoinColumn(name = "gramatura_id")
	private Gramatura gramatura;

	@ElementCollection
	@CollectionTable(name = "papel_precos")
	private List<PrecoVigencia> precos = new ArrayList<>();

	private BigDecimal markup;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Gramatura getGramatura() {
		return gramatura;
	}

	public void setGramatura(Gramatura gramatura) {
		this.gramatura = gramatura;
	}

	public void addPrecoVigencia(PrecoVigencia p) {
		this.precos.add(p);
	}

	public List<PrecoVigencia> getPrecos() {
		return precos;
	}

	public void setPrecos(List<PrecoVigencia> precos) {
		this.precos = precos;
	}

	public BigDecimal getCustoAtual() {
		return PrecoVigenciaService.getPrecoAtual(this.precos).getValor();
	}

	public BigDecimal getPrecoAtual() {
		return getCustoAtual().multiply(markup);
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

}
