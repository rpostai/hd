package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Strass extends BaseEntity {

	private TamanhoStrass tamanho;

	private BigDecimal markup = BigDecimal.ONE;

	@ElementCollection
	@CollectionTable(name = "strass_preco")
	private List<PrecoVigencia> precos = new ArrayList<>();

	public TamanhoStrass getTamanho() {
		return tamanho;
	}

	public void setTamanho(TamanhoStrass tamanho) {
		this.tamanho = tamanho;
	}

	public List<PrecoVigencia> getPrecos() {
		return precos;
	}

	public void addPreco(PrecoVigencia preco) {
		this.precos.add(preco);
	}

	public BigDecimal getPrecoVenda(int quantidade) {
		PrecoVigencia p = PrecoVigenciaService.getPrecoAtual(precos);
		return p.getValor().multiply(new BigDecimal(quantidade))
				.multiply(markup).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	private static enum TamanhoStrass {
		PEQUENO("P"), MEDIO("M"), GRANDE("G");

		private final String tamanho;

		private TamanhoStrass(String tamanho) {
			this.tamanho = tamanho;
		}

		public String getTamanho() {
			return tamanho;
		}

		public static TamanhoStrass getTamanhoStrass(String tamanho) {
			Optional<TamanhoStrass> result = Arrays.asList(values()).stream()
					.filter(x -> {
						return tamanho.equals(x.getTamanho());
					}).findFirst();
			return result.get();
		}

	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

}
