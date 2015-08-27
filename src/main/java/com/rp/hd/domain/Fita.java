package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Fita extends BaseEntity {
	
	private static final float TAMANHO_LACO_TRADICIONAL_EM_RELACAO_CONTORNO = 2.5f;

	@Convert(converter = TipoFitaConverter.class)
	@Column(name = "tipo_fita")
	private TipoFita tipoFita;

	@Convert(converter = NumeracaoFitaConverter.class)
	private NumeracaoFita numeracao;

	private BigDecimal markup;

	@ElementCollection
	@CollectionTable(name = "fita_precos")
	private List<PrecoVigencia> precos = new ArrayList<>();

	public TipoFita getTipoFita() {
		return tipoFita;
	}

	public void setTipoFita(TipoFita tipoFita) {
		this.tipoFita = tipoFita;
	}

	public NumeracaoFita getNumeracao() {
		return numeracao;
	}

	public void setNumeracao(NumeracaoFita numeracao) {
		this.numeracao = numeracao;
	}

	public List<PrecoVigencia> getPrecos() {
		return Collections.unmodifiableList(precos);
	}

	public void addPreco(PrecoVigencia p) {
		this.precos.add(p);
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public enum TipoFita {
		CETIM("C", "Cetim"), GORGURAO("G", "Gorgurão"), FIO_ENCERADO("F","Fio encerado"), SISAL("S", "Sisal"), APLIQUE_CETIM("A","Aplique de cetim");

		private final String tipo;
		private final String descricao;

		private TipoFita(String tipo, String descricao) {
			this.tipo = tipo;
			this.descricao = descricao;
		}

		public String getTipo() {
			return tipo;
		}

		public String getDescricao() {
			return descricao;
		}
		
		public static TipoFita getTipoFita(String valor) {
			Optional<TipoFita> result = Arrays.asList(values()).stream()
					.filter(x -> {
						return valor.equals(x.getTipo());
					}).findFirst();
			return result.get();
		}
		
		@Override
		public String toString() {
			return this.descricao;
		}

	}

	public enum NumeracaoFita {
		DOIS(2), TRES(3), CINCO(5), NOVE(9), INDEFINIDO(0);

		int numero;

		private NumeracaoFita(int numero) {
			this.numero = numero;
		}

		public int getNumero() {
			return numero;
		}

		public void setNumero(int numero) {
			this.numero = numero;
		}

		public static NumeracaoFita getNumeracaoFita(Integer valor) {
			Optional<NumeracaoFita> result = Arrays.asList(values()).stream()
					.filter(x -> {
						return valor.equals(x.getNumero());
					}).findFirst();
			return result.get();
		}
	}

	public BigDecimal getCustoAtual() {
		return PrecoVigenciaService.getPrecoAtual(this.precos).getValor();
	}
	
	public BigDecimal getPrecoCusto(ModeloConvite modelo) {
		if (this.tipoFita.equals(TipoFita.FIO_ENCERADO) || this.tipoFita.equals(TipoFita.SISAL)) {
			return getCustoAtual()
					.multiply(new BigDecimal(modelo.getQuantidadeFitaContorno() * TAMANHO_LACO_TRADICIONAL_EM_RELACAO_CONTORNO))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
		} else {
			return getCustoAtual()
					.multiply(new BigDecimal(modelo.getQuantidadeFitaContorno()))
					.setScale(2, BigDecimal.ROUND_HALF_UP);	
		}
	}

	public BigDecimal getPrecoVenda(ModeloConvite modelo) {
		return getPrecoCusto(modelo)
				.multiply(markup).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String toString() {
		return String.format("Fita %s número %d", tipoFita.getDescricao(),
				numeracao.getNumero());
	}

}
