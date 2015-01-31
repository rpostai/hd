package com.rp.hd.domain;

import javax.persistence.AttributeConverter;

import com.rp.hd.domain.Embalagem.TipoEmbalagem;

public class TipoEmbalagemConverter implements
		AttributeConverter<TipoEmbalagem, String> {

	@Override
	public String convertToDatabaseColumn(TipoEmbalagem obj) {
		return obj.name();
	}

	@Override
	public TipoEmbalagem convertToEntityAttribute(String valor) {
		return TipoEmbalagem.getTipoEmbalagem(valor);
	}

}
