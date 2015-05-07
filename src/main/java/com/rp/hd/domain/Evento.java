package com.rp.hd.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.rp.hd.domain.atendimento.EventoConvite;

@Entity
@Table(name = "evento")
@NamedQueries({ @NamedQuery(name = "Evento.TodosEventos", query = "select e from Evento e where inativo is null or inativo = false order by e.dataInicio desc"),
	@NamedQuery(name="Evento.TodosConvites", query="select c from EventoConvite c where c.evento = :evento order by c.codigo asc"),
	@NamedQuery(name="Evento.ConviteDetalhe", query="select c from EventoConvite c where c.evento = :evento and c.codigo = :codigo order by c.codigo asc")})
@XmlAccessorType(XmlAccessType.FIELD)
public class Evento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "data_inicio")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(name = "data_fim")
	@Temporal(TemporalType.DATE)
	private Date dataFim;

	@Column(name = "inativo")
	private Boolean inativo;

	@OneToMany(mappedBy = "evento", fetch=FetchType.LAZY)
	@XmlTransient
	private List<EventoConvite> convites;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<EventoConvite> getConvites() {
		return convites;
	}

	public void setConvites(List<EventoConvite> convites) {
		this.convites = convites;
	}

	public Boolean getInativo() {
		return inativo;
	}

	public void setInativo(Boolean inativo) {
		this.inativo = inativo;
	}

}
