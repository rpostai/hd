package com.rp.hd.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ModeloConviteFoto {

	private int ordem;

	private String caminho;

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

}
