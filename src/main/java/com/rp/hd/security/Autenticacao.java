package com.rp.hd.security;

import java.io.Serializable;

public class Autenticacao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3632484824643536152L;
	private String login;
	private String senha;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}