package com.rp.hd.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModeloFoto implements Serializable {

	private final Long id;
	private final String nome;
	private final List<Foto> foto = new ArrayList<Foto>();

	public ModeloFoto(Long id, String nome, Foto foto) {
		this.id = id;
		this.nome = nome;
		if (foto != null) {
			this.foto.add(foto);	
		}
	}
	
	public void addFoto(Foto foto) {
		this.foto.add(foto);
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Foto getFoto() {
		if (!foto.isEmpty()) {
			return foto.get(0);
		}
		return null;
	}
	
	public List<Foto> getFotos() {
		return this.foto;
	}	

}
