package com.rp.hd.domain;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.rp.hd.domain.utils.DateUtils;

public class PrecoVigenciaService {

	public static PrecoVigencia getPrecoAtual(List<PrecoVigencia> precos) {
		Date dataAtual = DateUtils.getDate();
		
		Optional<PrecoVigencia> preco = precos.stream().filter(p -> {
			return p.getVigencia().isDataDentroVigencia(dataAtual);
		}).findFirst();
		
		return preco.get();
	}

}
