package com.rp.hd.services;

public class Dado {

	private long id;

	private String nome;
	
	public Dado() {
	}

	public Dado(long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String descricao) {
		this.nome = descricao;
	}

}
