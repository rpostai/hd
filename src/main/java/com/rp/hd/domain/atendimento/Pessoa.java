package com.rp.hd.domain.atendimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.rp.hd.domain.BaseEntity;

@Entity
@Table(name = "pessoa")
public class Pessoa extends BaseEntity implements Serializable {

	private String nome;
	private String email;
	private String telefone;
	private String cpf;
	private String rg;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

}
