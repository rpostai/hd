package com.rp.hd.domain.atendimento;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rp.hd.domain.Evento;

@Entity
@Table(name = "evento_convite")
public class EventoConvite extends BaseConvite {

	@ManyToOne
	@JoinColumn(name = "evento_id")
	private Evento evento;

	@Column(name="codigo")
	private String codigo;

	@Column(name="preco_evento")
	private BigDecimal precoEvento;

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getPrecoEvento() {
		return precoEvento;
	}

	public void setPrecoEvento(BigDecimal precoEvento) {
		this.precoEvento = precoEvento;
	}

}
