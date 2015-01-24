package com.rp.hd.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Serigrafia extends BaseEntity {

	@Convert(converter=TipoSerigrafiaConverter.class)
	private TipoCobrancaSerigrafia tipoCobranca;

	@ElementCollection
	@CollectionTable(name = "serigrafia_precos")
	private List<PrecoVigencia> precos;

	public enum TipoCobrancaSerigrafia {
		POR_UNIDADE("U"), POR_VOLUME("V");

		private final String tipo;

		private TipoCobrancaSerigrafia(String tipo) {
			this.tipo = tipo;
		}

		public String getTipo() {
			return tipo;
		}
		
		public static TipoCobrancaSerigrafia getTipoCobrancaSerigrafia(String tipo) {
			
			Optional<TipoCobrancaSerigrafia> result = Arrays.asList(values()).stream()
					.filter(x -> {
						return tipo.equals(x.getTipo());
					}).findFirst();
			return result.get();
			
		}
	}

}
