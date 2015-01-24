package com.rp.hd.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rp.hd.domain.ModeloConvite.ModeloFaca;

@Converter
public class ModeloFacaConverter implements
		AttributeConverter<ModeloConvite.ModeloFaca, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ModeloFaca obj) {
		return obj.getValor();
	}

	@Override
	public ModeloFaca convertToEntityAttribute(Integer valor) {
		return ModeloFaca.getModeloFaca(valor);
	}

}
