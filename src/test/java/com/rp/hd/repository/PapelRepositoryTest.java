package com.rp.hd.repository;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.rp.hd.domain.Gramatura;
import com.rp.hd.domain.Papel;
import com.rp.hd.domain.PrecoVigencia;
import com.rp.hd.domain.Vigencia;
import com.rp.hd.domain.utils.DateUtils;
import com.rp.hd.repository.jpa.PapelRepository;

public class PapelRepositoryTest extends AbstractRepositoryTest {
	
	@Inject
	private PapelRepository repository;
	
	@Test
	public void deveInserirUmPapelSemPrecos() {
		Papel p1 = new Papel();
		p1.setMarkup(new BigDecimal("3.3"));
		p1.setNome("Aspen");
		p1.setGramatura(Gramatura.G250);
		repository.salvar(p1);
		Assert.assertNotNull(p1.getId());
	}
	
	@Test
	public void deveCriarUmPapelComPrecos() {
		
		System.setProperty("DATA", "2015-01-01");
		
		Papel p1 = new Papel();
		p1.setMarkup(new BigDecimal("2.2"));
		p1.setNome("Aspen");
		p1.setGramatura(Gramatura.G250);
		
		PrecoVigencia p = new PrecoVigencia();
		p.setValor(new BigDecimal("4.50"));
		
		Vigencia vigencia = new Vigencia();
		vigencia.setDataInicial(DateUtils.getDate());
		p.setVigencia(vigencia);
		p1.addPrecoVigencia(p);
		
		repository.salvar(p1);
		Assert.assertNotNull(p1.getId());
		
		Papel result = repository.get(p1.getId());
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getPrecoAtual(), new BigDecimal("9.90"));
		
	}

}
