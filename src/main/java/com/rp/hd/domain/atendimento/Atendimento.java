package com.rp.hd.domain.atendimento;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.RandomStringUtils;

@Entity
@Table(name = "atendimento")
public class Atendimento implements Serializable {

	private static Locale BRASIL = new Locale("pt", "BR");
	private static SimpleDateFormat SD = new SimpleDateFormat("YYYYMMDD",
			BRASIL);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "data_inicio", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataInicio;

	@Column(name = "data_fim", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataFim;

	private String numero;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "cliente1_id")
	private Pessoa cliente1;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "cliente2_id")
	private Pessoa cliente2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFim() {
		return dataFim;
	}

	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Pessoa getCliente1() {
		return cliente1;
	}

	public void setCliente1(Pessoa cliente1) {
		this.cliente1 = cliente1;
	}

	public Pessoa getCliente2() {
		return cliente2;
	}

	public void setCliente2(Pessoa cliente2) {
		this.cliente2 = cliente2;
	}

	public void iniciar() {
		this.dataInicio = Calendar.getInstance();
		this.numero = SD.format(Calendar.getInstance().getTime());
		this.numero += RandomStringUtils.randomNumeric(5);
	}

	public void finalizar() {
		this.dataFim = Calendar.getInstance();
	}

	public long getTempoTotalAtendimento() {
//		LocalDateTime d1 = LocalDateTime.of(dataInicio.get(Calendar.YEAR),
//				dataInicio.get(Calendar.MONTH),
//				dataInicio.get(Calendar.DAY_OF_MONTH),
//				dataInicio.get(Calendar.HOUR), dataInicio.get(Calendar.MINUTE),
//				dataInicio.get(Calendar.SECOND));
//
//		LocalDateTime d2 = LocalDateTime.of(dataFim.get(Calendar.YEAR),
//				dataFim.get(Calendar.MONTH),
//				dataFim.get(Calendar.DAY_OF_MONTH), dataFim.get(Calendar.HOUR),
//				dataFim.get(Calendar.MINUTE), dataFim.get(Calendar.SECOND));
//
//		return ChronoUnit.MINUTES.between(d2, d1);
		
		if (dataInicio != null && dataFim != null) {
			long diff = dataFim.getTime().getTime() - dataInicio.getTime().getTime();
			
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			return diffMinutes;	
		}
		
		return -1l;
		
		
	}

}
