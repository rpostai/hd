package com.rp.hd.domain;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rp.hd.domain.Calculadora.Orcamento;

public class CalculadoraTest {

	private BaseDados dados;

	@Before
	public void setup() {
		dados = new BaseDados();
	}

	@Test
	public void deveCalcularSomenteEnvelopeModeloGabrielaLinear() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("gabriela"))
				.papelEnvelope(dados.getPapel("linear"))
				.colagem(dados.getColagem()).build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("0.97"), orcamento.getItems().get(0)
				.getValor());
		System.out.println(orcamento);

	}

	@Test
	public void deveCalcularSomenteEnvelopeModeloPaulaMedioLinear() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("paulamedio"))
				.papelEnvelope(dados.getPapel("linear"))
				.colagem(dados.getColagem()).build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("1.49"), orcamento.getItems().get(0)
				.getValor());
		System.out.println(orcamento);

	}
	
	@Test
	public void deveCalcularSomentePapelInternoParaPaulaMedio() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("paulamedio"))
				.papelEnvelope(dados.getPapel("linear"))
				.papelInterno(dados.getPapel("aspen250"))
				.colagem(dados.getColagem()).build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("0.95"), orcamento.getItems().get(1)
				.getValor());
		System.out.println(orcamento);

	}
	
	@Test
	public void deveCalcularPaulaMedioComImpressao() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("paulamedio"))
				.papelEnvelope(dados.getPapel("linear"))
				.papelInterno(dados.getPapel("aspen180"))
				.colagem(dados.getColagem())
				.impressaoEnvelope(dados.getImpressao("1face"))
				.build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("1.16"), orcamento.getItems().get(2)
				.getValor());
		System.out.println(orcamento);

	}
	
	@Test
	public void deveCalcularPaulaMedioComFita() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("paulamedio"))
				.papelEnvelope(dados.getPapel("aspen250"))
				.fita(dados.getFita("cetim3"))
				.build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("0.29"), orcamento.getItems().get(1)
				.getValor());
		System.out.println(orcamento);
	}
	
	@Test
	public void deveCalcularImpressaoNomeConvidado() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("paulamedio"))
				.papelEnvelope(dados.getPapel("aspen250"))
				.impressaoNome(dados.getImpressaoNome("tag"))
				.build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("0.17"), orcamento.getItems().get(1)
				.getValor());
		System.out.println(orcamento);
	}
	
	@Test
	public void deveCalcularModeloComRenda() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("paulamedio"))
				.papelEnvelope(dados.getPapel("aspen250"))
				.renda(dados.getRenda())
				.build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("0.34"), orcamento.getItems().get(1)
				.getValor());
		System.out.println(orcamento);
	}
	
	@Test
	public void deveCalcularAplicacaoSerigrafiaInterno() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("paulamedio"))
				.papelEnvelope(dados.getPapel("aspen250"))
				.serigrafiaInterno(dados.getAplicacaoSerigrafiaInterno())
				.build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("1.2"), orcamento.getItems().get(1)
				.getValor());
		System.out.println(orcamento);
	}
	
	@Test
	public void deveCalcularAplicacaoHotStamp() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("paulamedio"))
				.papelEnvelope(dados.getPapel("aspen250"))
				.hotstamp(dados.getHotStamp())
				.build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("1.2"), orcamento.getItems().get(1)
				.getValor());
		System.out.println(orcamento);
	}
	
	@Test
	public void deveCalcularAplicacaoStrass() {
		Calculadora calculadora = Calculadora.CalculadoraBuilder.getInstance()
				.quantidadeConvites(100)
				.modeloConvite(dados.getModelo("paulamedio"))
				.papelEnvelope(dados.getPapel("aspen250"))
				.strass(dados.getStrass(), 4)
				.build();
		Orcamento orcamento = calculadora.calcular();
		Assert.assertEquals(new BigDecimal("0.26"), orcamento.getItems().get(1)
				.getValor());
		System.out.println(orcamento);
	}
	
	

}
