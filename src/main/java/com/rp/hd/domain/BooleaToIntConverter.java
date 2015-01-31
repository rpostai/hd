package com.rp.hd.domain;

import javax.persistence.AttributeConverter;

public class BooleaToIntConverter implements AttributeConverter<Boolean, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Boolean obj) {
		return obj ? 1 : 0;
	}

	@Override
	public Boolean convertToEntityAttribute(Integer valor) {
		return valor > 0 ;
	}

}
