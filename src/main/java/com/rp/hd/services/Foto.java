package com.rp.hd.services;

import java.io.Serializable;

public class Foto implements Serializable {

	private final int ordem;
	private final String caminho;

	public Foto(int ordem, String caminho) {
		super();
		this.ordem = ordem;
		this.caminho = caminho;
	}

	public int getOrdem() {
		return ordem;
	}

	public String getCaminho() {
		return caminho;
	}

}
