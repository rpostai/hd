package com.rp.hd.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rp.hd.domain.Fita.NumeracaoFita;

@Converter
public class NumeracaoFitaConverter implements AttributeConverter<Fita.NumeracaoFita, Integer>  {

	@Override
	public Integer convertToDatabaseColumn(NumeracaoFita obj) {
		return obj.getNumero();
	}

	@Override
	public NumeracaoFita convertToEntityAttribute(Integer valor) {
		return Fita.NumeracaoFita.getNumeracaoFita(valor);
	}
}
