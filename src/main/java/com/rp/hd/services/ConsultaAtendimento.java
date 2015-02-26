package com.rp.hd.services;

import java.io.Serializable;
import java.util.Date;

public class ConsultaAtendimento implements Serializable {

	private String nome;
	private String numero;
	private String data;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
