package com.rp.hd.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Optional;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ModeloConvite extends BaseEntity {

	private String codigo;

	private String nome;

	@Convert(converter = ModeloFacaConverter.class)
	private ModeloFaca modeloFaca;

	private boolean autoEnvelopado; // indica se o modelo é auto envelopado

	private int quantidadeFitaContorno;

	private int tamanhoItemInterno; // armazena quantas folhas por F0 é possivel
									// fazer por folha para este envelope

	private boolean rendaAplicavel; // indica se é aplicável renda ou não neste
									// modelo de envelope

	private int quantidadeRendaEmCentimetros;

	private boolean temColagem; // indica se este modelo tem colagem;

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
		
		BigDecimal valorPapel = papel.getPrecoAtual();
		BigDecimal valorColagem = BigDecimal.ZERO;
		if (temColagem) {
			valorColagem = colagem !=null ? colagem.getPrecoAtual() : BigDecimal.ZERO;
		}
		
		return valorPapel.divide(new BigDecimal(this.getModeloFaca().getValor())).add(valorColagem).setScale(2,BigDecimal.ROUND_HALF_UP);
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
