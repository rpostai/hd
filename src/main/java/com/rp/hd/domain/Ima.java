package com.rp.hd.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Ima extends BaseEntity {
	
	private int tamanho;
	
	private List<PrecoVigencia> precos = new ArrayList<>();

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public List<PrecoVigencia> getPrecos() {
		return precos;
	}

	public void addPreco(PrecoVigencia preco) {
		this.precos.add(preco);
	}
	
}
