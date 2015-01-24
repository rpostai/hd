package com.rp.hd.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rp.hd.domain.Fita.TipoFita;

@Converter
public class TipoFitaConverter implements
		AttributeConverter<Fita.TipoFita, String> {

	@Override
	public String convertToDatabaseColumn(TipoFita obj) {
		return obj.getTipo();
	}

	@Override
	public TipoFita convertToEntityAttribute(String valor) {
		return Fita.TipoFita.getTipoFita(valor);
	}
}
