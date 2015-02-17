package com.rp.hd.security;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.picketlink.idm.jpa.annotations.AttributeValue;
import org.picketlink.idm.jpa.annotations.entity.IdentityManaged;
import org.picketlink.idm.jpa.model.sample.simple.IdentityTypeEntity;

@Entity
@Table(name = "usuario")
@IdentityManaged(HdUser.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario extends IdentityTypeEntity {

	//@Column(name = "LOGIN", length = 50)
	@AttributeValue
	private String loginName;

	@Column(name = "NOME", length = 250)
	private String nome;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EMPRESA_ID")
	@AttributeValue
	private Empresa empresa;
	
	public String getLogin() {
		return loginName;
	}

	public void setLogin(String login) {
		this.loginName = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(loginName).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Usuario) {
			Usuario u = (Usuario) obj;
			return new EqualsBuilder().append(u.getLogin(), this.getLogin())
					.isEquals();
		}
		return false;
	}

}
