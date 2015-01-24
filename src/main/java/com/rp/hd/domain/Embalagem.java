package com.rp.hd.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Embalagem extends BaseEntity {

	private int altura;

	private int largura;

	private int densindade;
	
	@ElementCollection
	@CollectionTable(name="embalagem_preco")
	private List<PrecoVigencia> precos = new ArrayList<>();

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

	public int getDensindade() {
		return densindade;
	}

	public void setDensindade(int densindade) {
		this.densindade = densindade;
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
		PP, PE
	}

	@Override
	public String toString() {
		return String.format("%s-%dx%dx%d", tipoEmbalagem.name(), altura, largura,
				densindade);
	}
}
