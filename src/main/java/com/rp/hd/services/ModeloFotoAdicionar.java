package com.rp.hd.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModeloFotoAdicionar implements Serializable {

	private Long id;
	private String nome;
	private String caminhoFoto;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCaminhoFoto() {
		return caminhoFoto;
	}

}
