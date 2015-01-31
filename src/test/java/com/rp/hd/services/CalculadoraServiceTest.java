package com.rp.hd.services;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.hd.domain.Calculadora.Orcamento;

@UsingDataSet("cenario.xml")
@CleanupUsingScript("clean.sql")
public class CalculadoraServiceTest extends AbstractServiceTest {

	@Inject
	private CalculadoraService service;

	@Test
	public void deveRetornarTodosDadosParaPaginaCalculadora() {
		CalculadoraDados dados = service.getDadosCalculadora();
		Assert.assertNotNull(dados);

		Assert.assertEquals(5, dados.getPapeis().size());

		Assert.assertEquals(3, dados.getFitas().size());

		Assert.assertEquals(2, dados.getLacos().size());

		Assert.assertEquals(1, dados.getRendas().size());

		Assert.assertEquals(4, dados.getImpressoes().size());

		Assert.assertEquals(3, dados.getImpressoesNome().size());

		Assert.assertEquals(2, dados.getImas().size());

		Assert.assertEquals(2, dados.getSerigrafia().size());

		Assert.assertEquals(1, dados.getHotstamps().size());

		Assert.assertEquals(3, dados.getModelos().size());

	}

	@Test
	public void deveCalcularOPreco() {
		Orcamento o = service.calcular(150, 100l, 104l, 104l,
				0l, 100l, 100l, 101l, 100l,
				0l, 0l, 0l, 0l,
				0, 0l, 0l);
		Assert.assertNotNull(o);
		System.out.println(o);
	}

}
