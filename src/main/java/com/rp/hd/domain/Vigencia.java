package com.rp.hd.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Vigencia {

	@Column(name = "data_inicial")
	private Date dataInicial;

	@Column(name = "data_final")
	private Date dataFinal;
	
	public Vigencia() {
		
	}
	
	public Vigencia(Date dataInicial, Date dataFinal) {
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public boolean isDataDentroVigencia(Date date) {
		boolean result =  dataInicial.compareTo(date) <= 0;
		
		if (dataFinal != null) {
			result &= dataFinal.compareTo(date) >=0;
		}
		
		return result;
	}

}
