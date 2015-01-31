package com.rp.hd.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GramaturaConverter implements AttributeConverter<Gramatura, Integer>  {

	@Override
	public Integer convertToDatabaseColumn(Gramatura g) {
		return g.getValor();
	}

	@Override
	public Gramatura convertToEntityAttribute(Integer valor) {
		return Gramatura.getGramatura(valor);
	}
	
	

	
}
