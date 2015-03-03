package com.rp.hd.domain.atendimento;

import java.math.BigDecimal;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.rp.hd.domain.BooleaToIntConverter;

@Entity
@Table(name = "promocao")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries({ @NamedQuery(name = "PromocaoConvite.PromocoesAtivas", query = "select o from PromocaoConvite o where o.ativo = 1 order by valor asc") })
public class PromocaoConvite extends BaseConvite {

	private BigDecimal valor;

	private BigDecimal custo;

	@Convert(converter = BooleaToIntConverter.class)
	private boolean ativo;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
