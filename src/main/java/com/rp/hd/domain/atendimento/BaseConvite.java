package com.rp.hd.domain.atendimento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.rp.hd.domain.Acoplamento;
import com.rp.hd.domain.Cliche;
import com.rp.hd.domain.CorteEnvelope;
import com.rp.hd.domain.Fita;
import com.rp.hd.domain.HotStamp;
import com.rp.hd.domain.Ima;
import com.rp.hd.domain.Impressao;
import com.rp.hd.domain.ImpressaoNome;
import com.rp.hd.domain.Laco;
import com.rp.hd.domain.ModeloConvite;
import com.rp.hd.domain.Papel;
import com.rp.hd.domain.Renda;
import com.rp.hd.domain.Serigrafia;
import com.rp.hd.domain.Strass;

@MappedSuperclass
public abstract class BaseConvite implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@JoinColumn(name = "atendimento_id")
	@OneToOne
	private Atendimento atendimento;

	@ManyToOne
	@JoinColumn(name = "modelo_convite_id", nullable = false)
	private ModeloConvite modelo;

	@ManyToOne
	@JoinColumn(name = "papel_envelope_id")
	private Papel papelEnvelope;

	@ManyToOne
	@JoinColumn(name = "papel_interno_id")
	private Papel papelInterno;

	@ManyToOne
	@JoinColumn(name = "impressao_envelope_id")
	private Impressao impressaoEnvelope;

	@ManyToOne()
	@JoinColumn(name = "impressao_interno")
	private Impressao impressaoInterno;

	@ManyToOne
	@JoinColumn(name = "fita_id")
	private Fita fita;

	@ManyToOne
	@JoinColumn(name = "laco_id")
	private Laco laco;

	@ManyToOne
	@JoinColumn(name = "renda_id")
	private Renda renda;

	@ManyToOne
	@JoinColumn(name = "impressao_nome_id")
	private ImpressaoNome impressaoNome;

	@ManyToOne
	@JoinColumn(name = "serigrafia_interno_id")
	private Serigrafia serigrafiaInterno;

	@ManyToOne
	@JoinColumn(name = "serigrafia_envelope_id")
	private Serigrafia serigrafiaEnvelope;

	@ManyToOne
	@JoinColumn(name = "hot_stamp_id")
	private HotStamp hotstamp;

	@ManyToOne
	@JoinColumn(name = "strass_id")
	private Strass strass;

	private int quantidadeStrass;

	@ManyToOne
	@JoinColumn(name = "ima_id")
	private Ima ima;

	@ManyToOne
	@JoinColumn(name = "cliche_id")
	private Cliche cliche;

	@ManyToOne
	@JoinColumn(name = "acoplamento_envelope_id")
	private Acoplamento acoplamentoEnvelope;

	@ManyToOne
	@JoinColumn(name = "acoplamento_interno_id")
	private Acoplamento acoplamentoInterno;

	@ManyToOne
	@JoinColumn(name = "corte_interno_almofadado_id")
	private CorteEnvelope corteInternoAlmofadado;

	public ModeloConvite getModelo() {
		return modelo;
	}

	public void setModelo(ModeloConvite modelo) {
		this.modelo = modelo;
	}

	public Papel getPapelEnvelope() {
		return papelEnvelope;
	}

	public void setPapelEnvelope(Papel papelEnvelope) {
		this.papelEnvelope = papelEnvelope;
	}

	public Papel getPapelInterno() {
		return papelInterno;
	}

	public void setPapelInterno(Papel papelInterno) {
		this.papelInterno = papelInterno;
	}

	public Impressao getImpressaoEnvelope() {
		return impressaoEnvelope;
	}

	public void setImpressaoEnvelope(Impressao impressaoEnvelope) {
		this.impressaoEnvelope = impressaoEnvelope;
	}

	public Impressao getImpressaoInterno() {
		return impressaoInterno;
	}

	public void setImpressaoInterno(Impressao impressaoInterno) {
		this.impressaoInterno = impressaoInterno;
	}

	public Fita getFita() {
		return fita;
	}

	public void setFita(Fita fita) {
		this.fita = fita;
	}

	public Laco getLaco() {
		return laco;
	}

	public void setLaco(Laco laco) {
		this.laco = laco;
	}

	public Renda getRenda() {
		return renda;
	}

	public void setRenda(Renda renda) {
		this.renda = renda;
	}

	public ImpressaoNome getImpressaoNome() {
		return impressaoNome;
	}

	public void setImpressaoNome(ImpressaoNome impressaoNome) {
		this.impressaoNome = impressaoNome;
	}

	public Serigrafia getSerigrafiaInterno() {
		return serigrafiaInterno;
	}

	public void setSerigrafiaInterno(Serigrafia serigrafiaInterno) {
		this.serigrafiaInterno = serigrafiaInterno;
	}

	public Serigrafia getSerigrafiaEnvelope() {
		return serigrafiaEnvelope;
	}

	public void setSerigrafiaEnvelope(Serigrafia serigrafiaEnvelope) {
		this.serigrafiaEnvelope = serigrafiaEnvelope;
	}

	public HotStamp getHotstamp() {
		return hotstamp;
	}

	public void setHotstamp(HotStamp hotstamp) {
		this.hotstamp = hotstamp;
	}

	public Strass getStrass() {
		return strass;
	}

	public void setStrass(Strass strass) {
		this.strass = strass;
	}

	public int getQuantidadeStrass() {
		return quantidadeStrass;
	}

	public void setQuantidadeStrass(int quantidadeStrass) {
		this.quantidadeStrass = quantidadeStrass;
	}

	public Ima getIma() {
		return ima;
	}

	public void setIma(Ima ima) {
		this.ima = ima;
	}

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

	public Cliche getCliche() {
		return cliche;
	}

	public void setCliche(Cliche cliche) {
		this.cliche = cliche;
	}

	public Acoplamento getAcoplamentoEnvelope() {
		return acoplamentoEnvelope;
	}

	public void setAcoplamentoEnvelope(Acoplamento acoplamentoEnvelope) {
		this.acoplamentoEnvelope = acoplamentoEnvelope;
	}

	public Acoplamento getAcoplamentoInterno() {
		return acoplamentoInterno;
	}

	public void setAcoplamentoInterno(Acoplamento acoplamentoInterno) {
		this.acoplamentoInterno = acoplamentoInterno;
	}

	public CorteEnvelope getCorteInternoAlmofadado() {
		return corteInternoAlmofadado;
	}

	public void setCorteInternoAlmofadado(CorteEnvelope corteInternoAlmofadado) {
		this.corteInternoAlmofadado = corteInternoAlmofadado;
	}

}
