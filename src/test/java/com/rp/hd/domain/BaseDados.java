package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rp.hd.domain.Embalagem.TipoEmbalagem;
import com.rp.hd.domain.Fita.NumeracaoFita;
import com.rp.hd.domain.Fita.TipoFita;
import com.rp.hd.domain.ModeloConvite.ModeloFaca;
import com.rp.hd.domain.utils.DateUtils;

public class BaseDados {
	
	private static Vigencia vigencia;
	private BigDecimal MARKUP = new BigDecimal("3.3");
	
	private Map<Integer, Gramatura> gramaturas = new HashMap<>();
	private Map<String, Papel> papeis = new HashMap<>();
	private Map<String, Embalagem> embalagens = new HashMap<>();
	private Map<String, ModeloConvite> modelos = new HashMap<>();
	private Map<String, Impressao> impressoes = new HashMap<>();
	private Map<String, Fita> fitas = new HashMap<>();
	private Map<String, ImpressaoNome> impressaoNomes = new HashMap<>();
	private Renda renda;
	private Colagem colagem;
	private Serigrafia serigrafiaInterno;
	private Serigrafia serigrafiaEnvelope;
	private HotStamp hotStamp;
	private Strass strass;
	
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
		criaImpressoes();
		criaFitas();
		criaImpressaoNomes();
		criaRenda();
		criaSerigrafiaInterno();
		criaHotStamp();
		criaStrass();
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
	
	public void criaImpressoes() {
		Impressao face1 = new Impressao();
		face1.setDescricao("Impressão Digital 1 Face");
		face1.setMarkup(MARKUP);
		
		PrecoVigencia p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("0.35"));
		
		face1.addPreco(p);
		
		Impressao face2 = new Impressao();
		face2.setDescricao("Impressão Digital 2 Faces");
		face2.setMarkup(MARKUP);
		
		p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("0.60"));
		
		face2.addPreco(p);
		
		impressoes.put("1face", face1);
		impressoes.put("2face", face2);
		
	}
	
	public void criaFitas() {
		Fita f1 = new Fita();
		f1.setMarkup(MARKUP);
		f1.setNumeracao(NumeracaoFita.TRES);
		f1.setTipoFita(TipoFita.CETIM);
		
		PrecoVigencia p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("0.0025"));
		f1.addPreco(p);
		
		Fita f2 = new Fita();
		f2.setMarkup(MARKUP);
		f2.setNumeracao(NumeracaoFita.CINCO);
		f2.setTipoFita(TipoFita.CETIM);
		
		p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("0.0032"));
		f2.addPreco(p);
		
		fitas.put("cetim3", f1);
		fitas.put("cetim5", f2);
		
	}
	
	private void criaImpressaoNomes() {
		ImpressaoNome i1= new ImpressaoNome();
		i1.setDescricao("Tags 3x3 cm");
		i1.setMarkup(MARKUP);
		
		PrecoVigencia p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("0.05"));
		i1.addPreco(p);
		
		impressaoNomes.put("tag", i1);
		
		ImpressaoNome i2= new ImpressaoNome();
		i2.setDescricao("Impressao Verso Parcial");
		i2.setMarkup(MARKUP);
		
		p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("0.15"));
		i2.addPreco(p);
		
		impressaoNomes.put("versoparcial", i2);
		
		ImpressaoNome i3= new ImpressaoNome();
		i3.setDescricao("Impressao Verso Total");
		i3.setMarkup(MARKUP);
		
		p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("0.30"));
		i3.addPreco(p);
		
		impressaoNomes.put("versototal", i3);
		
	}
	
	private void criaRenda() {
		this.renda = new Renda();
		renda.setDescricao("Renda Branca");
		renda.setMarkup(MARKUP);

		PrecoVigencia p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("0.0058"));
		renda.addPreco(p);
	}
	
	public void criaSerigrafiaInterno() {
		this.serigrafiaInterno = new Serigrafia();
		
		PrecoVigencia p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("1.2"));
		serigrafiaInterno.addPreco(p);
		
	}
	
	public void criaSerigrafiaEnvelope() {
		this.serigrafiaEnvelope = new Serigrafia();
		
		PrecoVigencia p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("1.2"));
		serigrafiaEnvelope.addPreco(p);
	}
	
	public void criaHotStamp() {
		hotStamp = new HotStamp();
		
		PrecoVigencia p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("1.2"));
		hotStamp.addPreco(p);
	}
	
	public void criaStrass() {
		strass = new Strass();
		strass.setMarkup(MARKUP);
		PrecoVigencia p = new PrecoVigencia();
		p.setVigencia(vigencia);
		p.setValor(new BigDecimal("0.02"));
		strass.addPreco(p);
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
	
	public Impressao getImpressao(String chave) {
		return impressoes.get(chave);
	}
	
	public Fita getFita(String chave) {
		return fitas.get(chave);
	}
	
	public ImpressaoNome getImpressaoNome(String chave) {
		return impressaoNomes.get(chave);
	}
	
	public Renda getRenda() {
		return this.renda;
	}
	
	public Serigrafia getAplicacaoSerigrafiaInterno() {
		return this.serigrafiaInterno;
	}
	
	public Serigrafia getAplicacaoSerigrafiaEnvelope() {
		return this.serigrafiaEnvelope;
	}
	
	public HotStamp getHotStamp() {
		return this.hotStamp;
	}
	
	public Strass getStrass() {
		return this.strass;
	}

}
