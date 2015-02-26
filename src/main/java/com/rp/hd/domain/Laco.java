package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Laco extends BaseEntity {

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "qtd_centimetros_fita")
	private int quantidadeFitaEmCentimetros;

	private BigDecimal markup = BigDecimal.ONE;

	@ElementCollection
	@CollectionTable(name = "laco_maoobra_preco")
	private List<PrecoVigencia> custoMaoDeObra;

	public int getQuantidadeFitaEmCentimetros() {
		return quantidadeFitaEmCentimetros;
	}

	public void setQuantidadeFitaEmCentimetros(int quantidadeFitaEmCentimetros) {
		this.quantidadeFitaEmCentimetros = quantidadeFitaEmCentimetros;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<PrecoVigencia> getCustoMaoDeObra() {
		return custoMaoDeObra;
	}

	public void addCustoMaoDeObra(PrecoVigencia custoMaoDeObra) {
		this.custoMaoDeObra.add(custoMaoDeObra);
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public void setCustoMaoDeObra(List<PrecoVigencia> custoMaoDeObra) {
		this.custoMaoDeObra = custoMaoDeObra;
	}
	
	public BigDecimal getCustoAtualMaoObra() {
		return PrecoVigenciaService.getPrecoAtual(this.custoMaoDeObra).getValor();
	}
	
	public BigDecimal getCusto(Fita fita) {
		BigDecimal precoLacoComFita = fita.getCustoAtual().multiply(new BigDecimal(quantidadeFitaEmCentimetros));
		return precoLacoComFita.add(getCustoAtualMaoObra());
	}
	
	public BigDecimal getPrecoVenda(Fita fita) {
		BigDecimal precoLacoComFita = fita.getCustoAtual().multiply(new BigDecimal(quantidadeFitaEmCentimetros));
		precoLacoComFita = precoLacoComFita.multiply(markup).setScale(2, BigDecimal.ROUND_HALF_UP);
		precoLacoComFita = precoLacoComFita.add(getCustoAtualMaoObra());
		return precoLacoComFita;
	}
}
