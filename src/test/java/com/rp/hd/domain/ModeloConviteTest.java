package com.rp.hd.domain;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModeloConviteTest {

	private BaseDados dados;

	@Before
	public void setup() {
		dados = new BaseDados();
	}

	@Test
	public void deveCalcularModeloGabrielaLinear() {
		ModeloConvite gabriela = dados.getModelo("gabriela");
		Papel linear = dados.getPapel("linear");
		BigDecimal valor = gabriela.getPrecoVenda(linear);
		Assert.assertEquals(new BigDecimal("0.97"), valor);
	}
	
	@Test
	public void deveCalcularModeloGabrielaAspen250() {
		ModeloConvite gabriela = dados.getModelo("gabriela");
		Papel linear = dados.getPapel("aspen250");
		BigDecimal valor = gabriela.getPrecoVenda(linear);
		Assert.assertEquals(new BigDecimal("1.90"), valor);
	}
	
	@Test
	public void deveCalcularModeloGabrielaAspen180() {
		ModeloConvite gabriela = dados.getModelo("gabriela");
		Papel linear = dados.getPapel("aspen180");
		BigDecimal valor = gabriela.getPrecoVenda(linear);
		Assert.assertEquals(new BigDecimal("1.32"), valor);
	}
	

	@Test
	public void deveCalcularPaulaMedioLinear() {
		ModeloConvite paulamedio = dados.getModelo("paulamedio");
		Papel linear = dados.getPapel("linear");
		
		Colagem colagem = dados.getColagem();
		
		BigDecimal valor = paulamedio.getPrecoVenda(linear);
		Assert.assertEquals(new BigDecimal("1.49"), valor);
	}

}
