package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converts;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="modelo_convite")
public class ModeloConvite extends BaseEntity {

	@Column(name="codigo")
	private String codigo;

	@Column(name="nome")
	private String nome;

	@Column(name="modelo_faca")
	@Convert(converter = ModeloFacaConverter.class)
	private ModeloFaca modeloFaca;

	@Column(name="auto_envelopado")
	@Convert(converter=BooleaToIntConverter.class)
	private boolean autoEnvelopado; // indica se o modelo � auto envelopado

	@Column(name="quantidade_fita_contorno")
	private int quantidadeFitaContorno;

	@Column(name="tamanho_item_interno")
	private int tamanhoItemInterno; // armazena quantas folhas por F0 � possivel
									// fazer por folha para este envelope

	@Column(name="renda_aplicavel")
	@Convert(converter=BooleaToIntConverter.class)
	private boolean rendaAplicavel; // indica se � aplic�vel renda ou n�o neste
									// modelo de envelope

	
	@Column(name="quantidade_renda_cm")
	private int quantidadeRendaEmCentimetros;

	@Column(name="tem_colagem")
	@Convert(converter=BooleaToIntConverter.class)
	private boolean temColagem; // indica se este modelo tem colagem;
	
	@Transient
	private Papel papel;

	@ManyToOne
	@JoinColumn(name = "embalagem_id")
	private Embalagem embalagem;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ModeloFaca getModeloFaca() {
		return modeloFaca;
	}

	public void setModeloFaca(ModeloFaca modeloFaca) {
		this.modeloFaca = modeloFaca;
	}

	public boolean isAutoEnvelopado() {
		return autoEnvelopado;
	}

	public void setAutoEnvelopado(boolean autoEnvelopado) {
		this.autoEnvelopado = autoEnvelopado;
	}

	public int getQuantidadeFitaContorno() {
		return quantidadeFitaContorno;
	}

	public void setQuantidadeFitaContorno(int quantidadeFitaContorno) {
		this.quantidadeFitaContorno = quantidadeFitaContorno;
	}

	public int getTamanhoItemInterno() {
		return tamanhoItemInterno;
	}

	public void setTamanhoItemInterno(int tamanhoItemInterno) {
		this.tamanhoItemInterno = tamanhoItemInterno;
	}

	public boolean isRendaAplicavel() {
		return rendaAplicavel;
	}

	public void setRendaAplicavel(boolean rendaAplicavel) {
		this.rendaAplicavel = rendaAplicavel;
	}

	public int getQuantidadeRendaEmCentimetros() {
		return quantidadeRendaEmCentimetros;
	}

	public void setQuantidadeRendaEmCentimetros(int quantidadeRendaEmCentimetros) {
		this.quantidadeRendaEmCentimetros = quantidadeRendaEmCentimetros;
	}

	public boolean isTemColagem() {
		return temColagem;
	}

	public void setTemColagem(boolean temColagem) {
		this.temColagem = temColagem;
	}

	public Embalagem getEmbalagem() {
		return embalagem;
	}

	public void setEmbalagem(Embalagem embalagem) {
		this.embalagem = embalagem;
	}
	
	public BigDecimal getPrecoVenda(Papel papel, Colagem colagem) {
		this.papel = papel;
		BigDecimal valorPapel = papel.getPrecoAtual();
		BigDecimal valorColagem = BigDecimal.ZERO;
		if (temColagem) {
			valorColagem = colagem !=null ? colagem.getPrecoAtual() : BigDecimal.ZERO;
		}
		
		return valorPapel.divide(new BigDecimal(this.getModeloFaca().getValor())).add(valorColagem).setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	
	@Override
	public String toString() {
		String modelo = String.format("Envelope Modelo %s", getNome());
		String papelStr = "";
		if (papel != null) {
			papelStr = String.format("Papel %s %dg", papel.getNome(), papel.getGramatura().getValor() );
			return String.format("%s - %s", modelo, papelStr);
		}
		return modelo;
	}



	public static enum ModeloFaca {
		FORMATO1(1), FORMATO2(2), FORMATO4(4), FORMATO6(6), FORMATO8(8);

		private int valor;

		private ModeloFaca(int valor) {
			this.valor = valor;
		}

		public int getValor() {
			return valor;
		}

		public void setValor(int valor) {
			this.valor = valor;
		}

		public static ModeloFaca getModeloFaca(Integer valor) {
			Optional<ModeloFaca> result = Arrays.asList(values()).stream()
					.filter(x -> {
						return valor.equals(x.getValor());
					}).findFirst();
			return result.get();
		}

	}

}
