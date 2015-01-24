package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rp.hd.domain.Embalagem.TipoEmbalagem;
import com.rp.hd.domain.ModeloConvite.ModeloFaca;
import com.rp.hd.domain.utils.DateUtils;

public class BaseDados {
	
	private static Vigencia vigencia;
	
	private Map<Integer, Gramatura> gramaturas = new HashMap<>();
	private Map<String, Papel> papeis = new HashMap<>();
	private Map<String, Embalagem> embalagens = new HashMap<>();
	private Map<String, ModeloConvite> modelos = new HashMap<>();
	private Colagem colagem;
	
	static {
		Date d1 = DateUtils.getDate();
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		c1.add(Calendar.DAY_OF_MONTH, -30);
		
		vigencia = new Vigencia(d1, null);
	}
	
	public BaseDados() {
		criaColagem();
		criaGramaturas();
		criaPapeis();
		criaEmbalagens();
		criaModelos();
	}
	

	public void criaColagem() {
		colagem = new Colagem();
		colagem.setDescricao("Colagem simples");
		colagem.setMarkup(BigDecimal.ONE);
		
		PrecoVigencia p1 = new PrecoVigencia();
		p1.setValor(new BigDecimal("0.2"));
		p1.setVigencia(vigencia);
		
		colagem.addPreco(p1);
	}
	
	public void criaGramaturas() {
		Gramatura g180 = new Gramatura();
		g180.setId(1l);
		g180.setValor(180);

		Gramatura g240 = new Gramatura();
		g240.setId(1l);
		g240.setValor(240);
		
		Gramatura g250 = new Gramatura();
		g250.setId(1l);
		g250.setValor(250);
		
		gramaturas.put(180, g180);
		gramaturas.put(240, g240);
		gramaturas.put(250, g250);
	}

	public void criaPapeis() {
		
		Papel aspen250 = new Papel();
		aspen250.setGramatura(gramaturas.get(240));
		aspen250.setNome("Color Plus Metalico Aspen");
		aspen250.setMarkup(new BigDecimal("3.3"));
		
		PrecoVigencia p1 = new PrecoVigencia();
		p1.setValor(new BigDecimal("4.60"));
		p1.setVigencia(vigencia);
		
		aspen250.addPrecoVigencia(p1);
		
		papeis.put("aspen250", aspen250);
		
		Papel aspen180 = new Papel();
		aspen180.setGramatura(gramaturas.get(240));
		aspen180.setNome("Color Plus Metalico Aspen");
		aspen180.setMarkup(new BigDecimal("3.3"));
		
		PrecoVigencia p2 = new PrecoVigencia();
		p2.setValor(new BigDecimal("3.20"));
		p2.setVigencia(vigencia);
		
		aspen180.addPrecoVigencia(p2);
		
		papeis.put("aspen180", aspen180);
		
		Papel linear = new Papel();
		linear.setNome("Evenglow Opalina Diamond Linear 240g");
		linear.setGramatura(gramaturas.get(240));
		linear.setMarkup(new BigDecimal("3.3"));
		
		PrecoVigencia p3 = new PrecoVigencia();
		p3.setValor(new BigDecimal("2.35"));
		p3.setVigencia(vigencia);
		linear.addPrecoVigencia(p3);
		
		papeis.put("linear", linear);
		
	}
	
	private void criaEmbalagens() {
		PrecoVigencia p1 = new PrecoVigencia();
		p1.setVigencia(vigencia);
		p1.setValor(new BigDecimal("0.06"));
		
		Embalagem pp1 = new Embalagem();
		pp1.setAltura(15);
		pp1.setLargura(25);
		pp1.setDensindade(10);
		pp1.setTipoEmbalagem(TipoEmbalagem.PP);
		pp1.addPreco(p1);
		
		embalagens.put("15x25x10", pp1);
		
		PrecoVigencia p2 = new PrecoVigencia();
		p2.setVigencia(vigencia);
		p2.setValor(new BigDecimal("0.08"));
		
		Embalagem pp2 = new Embalagem();
		pp2.setAltura(15);
		pp2.setLargura(25);
		pp2.setDensindade(10);
		pp2.setTipoEmbalagem(TipoEmbalagem.PP);
		pp2.addPreco(p1);
		
		embalagens.put("18x30x10", pp2);
		
	}
	
	public void criaModelos() {
		ModeloConvite gabriela = new ModeloConvite();
		gabriela.setCodigo("001");
		gabriela.setNome("Gabriela");
		gabriela.setModeloFaca(ModeloFaca.FORMATO8);
		gabriela.setAutoEnvelopado(false);
		gabriela.setRendaAplicavel(false);
		gabriela.setTemColagem(false);
		gabriela.setQuantidadeFitaContorno(29);
		gabriela.setTamanhoItemInterno(16);
		gabriela.setEmbalagem(embalagens.get("15x25x10"));
		
		modelos.put("gabriela", gabriela);
		
		ModeloConvite paulamedio = new ModeloConvite();
		paulamedio.setCodigo("002");
		paulamedio.setNome("Paula Médio");
		paulamedio.setModeloFaca(ModeloFaca.FORMATO6);
		paulamedio.setAutoEnvelopado(false);
		paulamedio.setRendaAplicavel(true);
		paulamedio.setTemColagem(true);
		paulamedio.setQuantidadeFitaContorno(35);
		paulamedio.setTamanhoItemInterno(16);
		paulamedio.setQuantidadeRendaEmCentimetros(18);
		paulamedio.setEmbalagem(embalagens.get("18x30x10"));
		
		modelos.put("paulamedio", paulamedio);
		
	}
	
	public Papel getPapel(String chave) {
		return papeis.get(chave);
	}
	
	public ModeloConvite getModelo(String chave) {
		return modelos.get(chave);
	}
	
	public Colagem getColagem() {
		return this.colagem;
	}

}
