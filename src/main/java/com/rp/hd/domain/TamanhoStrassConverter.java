package com.rp.hd.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rp.hd.domain.Strass.TamanhoStrass;

@Converter(autoApply = true)
public class TamanhoStrassConverter implements
		AttributeConverter<Strass.TamanhoStrass, String> {

	@Override
	public String convertToDatabaseColumn(TamanhoStrass valor) {
		return valor.getTamanho();
	}

	@Override
	public TamanhoStrass convertToEntityAttribute(String valor) {
		return TamanhoStrass.getTamanhoStrass(valor);
	}

}
