package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Embalagem extends BaseEntity {

	private int altura;

	private int largura;

	private int densidade;
	
	private BigDecimal markup = BigDecimal.ONE;

	@ElementCollection
	@CollectionTable(name = "embalagem_preco")
	private List<PrecoVigencia> precos = new ArrayList<>();

	@Column(name="tipo_embalagem")
	@Convert(converter=TipoEmbalagemConverter.class)
	private TipoEmbalagem tipoEmbalagem;

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public int getDensidade() {
		return densidade;
	}

	public void setDensidade(int densindade) {
		this.densidade = densindade;
	}

	public TipoEmbalagem getTipoEmbalagem() {
		return tipoEmbalagem;
	}

	public void setTipoEmbalagem(TipoEmbalagem tipoEmbalagem) {
		this.tipoEmbalagem = tipoEmbalagem;
	}

	public List<PrecoVigencia> getPrecos() {
		return Collections.unmodifiableList(precos);
	}

	public void addPreco(PrecoVigencia p) {
		this.precos.add(p);
	}

	public static enum TipoEmbalagem {
		PP, PE;
		
		public static TipoEmbalagem getTipoEmbalagem(String tipo) {
			return Arrays.asList(values()).stream().filter(emb -> {
				return emb.name().equals(tipo);
			}).findFirst().get();
		}
	}
	
	public BigDecimal getCustoAtual() {
		return PrecoVigenciaService.getPrecoAtual(this.precos).getValor();
	}

	public BigDecimal getPrecoVenda() {
		return getCustoAtual()
				.multiply(markup).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String toString() {
		return String.format("%s-%dx%dx%d", tipoEmbalagem.name(), altura,
				largura, densidade);
	}
}
