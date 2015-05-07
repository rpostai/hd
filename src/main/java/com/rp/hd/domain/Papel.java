package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Papel extends BaseEntity {

	private String nome;

	@Convert(converter = GramaturaConverter.class)
	private Gramatura gramatura;

	@ElementCollection
	@CollectionTable(name = "papel_precos")
	private List<PrecoVigencia> precos = new ArrayList<>();

	@Column(name = "inativo")
	private boolean inativo;

	@Column(name = "preco")
	private BigDecimal preco;

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
		// return PrecoVigenciaService.getPrecoAtual(this.precos).getValor();
		return this.getPreco();
	}

	public BigDecimal getPrecoAtual() {
		return getCustoAtual().multiply(markup).setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public boolean isInativo() {
		return inativo;
	}

	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

}
