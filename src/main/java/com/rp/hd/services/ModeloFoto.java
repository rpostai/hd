package com.rp.hd.services;

import java.io.Serializable;

public class ModeloFoto implements Serializable {

	private final Long id;
	private final String nome;
	private final String foto;

	public ModeloFoto(Long id, String nome, String foto) {
		this.id = id;
		this.nome = nome;
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getFoto() {
		return foto;
	}

}
