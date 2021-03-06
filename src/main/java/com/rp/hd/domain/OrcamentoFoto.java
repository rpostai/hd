package com.rp.hd.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.rp.hd.domain.atendimento.Atendimento;

@Entity
@Table(name = "orcamento_foto")
@NamedQueries({
	@NamedQuery(name="OrcamentoFotos.PorAtendimento", query="select o from OrcamentoFoto o where o.atendimento.id = :id"),
	@NamedQuery(name="OrcamentoFotos.RemoverPorAtendimento", query="delete from OrcamentoFoto o where o.atendimento.id = :id")
})
public class OrcamentoFoto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "atendimento_id")
	private Atendimento atendimento;

	@ManyToOne
	@JoinColumn(name = "modelo_id")
	private ModeloConvite modelo;

	@Column(name = "caminho_foto")
	private String caminhoFoto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public ModeloConvite getModelo() {
		return modelo;
	}

	public void setModelo(ModeloConvite modelo) {
		this.modelo = modelo;
	}

	public String getCaminhoFoto() {
		return caminhoFoto;
	}

	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}

}
