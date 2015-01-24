package com.rp.hd.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;

public class Fita extends BaseEntity {

	@Convert(converter = TipoFitaConverter.class)
	private TipoFita tipoFita;

	@Convert(converter = NumeracaoFitaConverter.class)
	private NumeracaoFita numeracao;
	
	@ElementCollection
	@CollectionTable(name="fita_precos")
	private List<PrecoVigencia> precos;

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

	public enum TipoFita {
		CETIM("C"), GORGURAO("G");

		private String tipo;

		private TipoFita(String tipo) {
			this.tipo = tipo;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public static TipoFita getTipoFita(String valor) {
			Optional<TipoFita> result = Arrays.asList(values()).stream()
					.filter(x -> {
						return valor.equals(x.getTipo());
					}).findFirst();
			return result.get();
		}

	}

	public enum NumeracaoFita {
		DOIS(2), TRES(3), CINCO(5), NOVE(9);

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

}
