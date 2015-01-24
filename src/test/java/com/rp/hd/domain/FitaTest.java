package com.rp.hd.domain;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class FitaTest extends AbstractTest {

	@Test
	public void deveCalcularValorFitaModeloGabriela() {
		ModeloConvite gabriela = dados.getModelo("gabriela");
		Fita fita = dados.getFita("cetim3");
		BigDecimal valor = fita.getPrecoVenda(gabriela);
		Assert.assertEquals(new BigDecimal("0.24"), valor);
	}
	
	@Test
	public void deveCalcularValorFitaModeloPaulaMedio() {
		ModeloConvite modelo = dados.getModelo("paulamedio");
		Fita fita = dados.getFita("cetim5");
		BigDecimal valor = fita.getPrecoVenda(modelo);
		Assert.assertEquals(new BigDecimal("0.37"), valor);
	}
}
