package com.rp.hd.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rp.hd.domain.Serigrafia.TipoCobrancaSerigrafia;

@Converter
public class TipoSerigrafiaConverter implements
		AttributeConverter<Serigrafia.TipoCobrancaSerigrafia, String> {

	@Override
	public String convertToDatabaseColumn(TipoCobrancaSerigrafia obj) {
		return obj.getTipo();
	}

	@Override
	public TipoCobrancaSerigrafia convertToEntityAttribute(String valor) {
		return TipoCobrancaSerigrafia.getTipoCobrancaSerigrafia(valor);
	}

}
