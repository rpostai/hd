package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rp.hd.domain.Calculadora.Orcamento.Item;

public class Calculadora {

	private final int quantidadeConvites;
	private final ModeloConvite modelo;
	private final Papel papelEnvelope;
	private final Papel papelInterno;
	private final Impressao impressaoEnvelope;
	private final Impressao impressaoInterno;
	private final Fita fita;
	private final Laco laco;
	private final HotStamp hotStamp;
	private final Serigrafia serigrafiaEnvelope;
	private final Serigrafia serigrafiaInterno;
	private final Renda renda;
	private final Ima ima;
	private final int quantidadeStrass;
	private final Strass strass;
	private final ImpressaoNome impressaoNome;
	private final Embalagem embalagem;

	private Calculadora(int quantidadeConvites, ModeloConvite modelo,
			Papel papelEnvelope, Papel papelInterno,
			Impressao impressaoEnvelope, Impressao impressaoInterno, Fita fita,
			Laco laco, HotStamp hotStamp, Serigrafia serigrafiaEnvelope,
			Serigrafia serigrafiaInterno, Renda renda, Ima ima,
			int quantidadeStrass, Strass strass, ImpressaoNome impressaoNome,
			Embalagem embalagem) {
		this.quantidadeConvites = quantidadeConvites;
		this.modelo = modelo;
		this.papelEnvelope = papelEnvelope;
		this.papelInterno = papelInterno;
		this.impressaoEnvelope = impressaoEnvelope;
		this.impressaoInterno = impressaoInterno;
		this.fita = fita;
		this.laco = laco;
		this.hotStamp = hotStamp;
		this.serigrafiaEnvelope = serigrafiaEnvelope;
		this.serigrafiaInterno = serigrafiaInterno;
		this.renda = renda;
		this.ima = ima;
		this.quantidadeStrass = quantidadeStrass;
		this.strass = strass;
		this.impressaoNome = impressaoNome;
		this.embalagem = embalagem;
	}
	
	public Orcamento calcular() {
		Orcamento o = new Orcamento();
		return o;
	}
	
	public Item calcularPrecoModeloConvite() {
		this.modelo.get
	}
	
	public class Orcamento {

		private List<Item> items = new ArrayList<>();
		private BigDecimal precoFinal;

		public List<Item> getItems() {
			return items;
		}

		public void addItem(Item item) {
			this.items.add(item);
		}

		public BigDecimal getPrecoFinal() {
			return precoFinal;
		}

		public void setPrecoFinal(BigDecimal precoFinal) {
			this.precoFinal = precoFinal;
		}

		public class Item {
			private final String item;
			private final BigDecimal valor;

			public Item(String item, BigDecimal valor) {
				this.item = item;
				this.valor = valor;
			}

			public String getItem() {
				return item;
			}

			public BigDecimal getValor() {
				return valor;
			}

		}

	}

	public static class CalculadoraBuilder {

		private int quantidadeConvites;
		private ModeloConvite modelo;
		private Papel papelEnvelope;
		private Papel papelInterno;
		private Impressao impressaoEnvelope;
		private Impressao impressaoInterno;
		private Fita fita;
		private Laco laco;
		private HotStamp hotStamp;
		private Serigrafia serigrafiaEnvelope;
		private Serigrafia serigrafiaInterno;
		private Renda renda;
		private Ima ima;
		private int quantidadeStrass;
		private Strass strass;
		private ImpressaoNome impressaoNome;
		private Embalagem embalagem;

		public CalculadoraBuilder quantidadeConvites(int quantidade) {
			this.quantidadeConvites = quantidade;
			return this;
		}

		public CalculadoraBuilder modeloConvite(ModeloConvite modelo) {
			this.modelo = modelo;
			return this;
		}

		public CalculadoraBuilder papelEnvelope(Papel papel) {
			this.papelEnvelope = papel;
			return this;
		}

		public CalculadoraBuilder papelInterno(Papel papel) {
			this.papelInterno = papel;
			return this;
		}

		public CalculadoraBuilder impressaoEnvelope(Impressao impressao) {
			this.impressaoEnvelope = impressao;
			return this;
		}

		public CalculadoraBuilder impressaoInterno(Impressao impressao) {
			this.impressaoInterno = impressao;
			return this;
		}

		public CalculadoraBuilder fita(Fita fita) {
			this.fita = fita;
			return this;
		}

		public CalculadoraBuilder laco(Laco laco) {
			this.laco = laco;
			return this;
		}

		public CalculadoraBuilder hotstamp(HotStamp hotStamp) {
			this.hotStamp = hotStamp;
			return this;
		}

		public CalculadoraBuilder serigrafiaInterno(Serigrafia serigrafia) {
			this.serigrafiaInterno = serigrafia;
			return this;
		}

		public CalculadoraBuilder serigrafiaEnvelope(Serigrafia serigrafia) {
			this.serigrafiaEnvelope = serigrafia;
			return this;
		}

		public CalculadoraBuilder renda(Renda renda) {
			this.renda = renda;
			return this;
		}

		public CalculadoraBuilder ima(Ima ima) {
			this.ima = ima;
			return this;
		}

		public CalculadoraBuilder strass(Strass strass, int quantidade) {
			this.strass = strass;
			this.quantidadeStrass = quantidade;
			return this;
		}

		public CalculadoraBuilder impressaoNome(ImpressaoNome impressao) {
			this.impressaoNome = impressao;
			return this;
		}

		public CalculadoraBuilder embalagem(Embalagem embalagem) {
			this.embalagem = embalagem;
			return this;
		}

		public Calculadora build() {

		}
	}

}
