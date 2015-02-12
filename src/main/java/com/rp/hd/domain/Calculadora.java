package com.rp.hd.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.rp.hd.domain.exceptions.ModeloInvalidoException;
import com.rp.hd.domain.exceptions.PapelInvalidoException;

public class Calculadora {

	private final int quantidadeConvites;
	private final ModeloConvite modelo;
	private final Colagem colagem;
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
	private final CorteEnvelope corte;

	private Calculadora(int quantidadeConvites, ModeloConvite modelo,
			Colagem colagem, Papel papelEnvelope, Papel papelInterno,
			Impressao impressaoEnvelope, Impressao impressaoInterno, Fita fita,
			Laco laco, HotStamp hotStamp, Serigrafia serigrafiaEnvelope,
			Serigrafia serigrafiaInterno, Renda renda, Ima ima,
			int quantidadeStrass, Strass strass, ImpressaoNome impressaoNome,
			CorteEnvelope corte) {
		this.quantidadeConvites = quantidadeConvites;
		this.modelo = modelo;
		this.colagem = colagem;
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
		this.embalagem = modelo.getEmbalagem();
		this.corte = corte;
	}

	public Orcamento calcular() {
		Orcamento o = new Orcamento();
		calcularPrecoModeloConvite(o);
		calcularPapelInterno(o);
		calcularValorImpressaoEnvelope(o);
		calcularValorImpressaoInterno(o);
		calcularValorFita(o);
		calcularImpressaoNome(o);
		calcularRenda(o);
		calcularAplicacaoSerigrafiaInterno(o);
		calcularAplicacaoSerigrafiaEnvelope(o);
		calcularHotStamping(o);
		calcularAplicacaoStrass(o);
		calcularPrecoCorte(o);
		calcularEmbalagem(o);
		return o;
	}

	public void calcularPrecoModeloConvite(Orcamento o) {
		BigDecimal valor = this.modelo.getPrecoVenda(papelEnvelope, colagem);
		o.addItem(o.new Item(this.modelo.toString(), valor));
	}

	public void calcularPapelInterno(Orcamento o) {
		int quantidadeFolhasParaPapelInterno = modelo.getTamanhoItemInterno();
		if (papelInterno != null && quantidadeFolhasParaPapelInterno > 0) {
			BigDecimal valorPapel = this.papelInterno.getPrecoAtual();
			valorPapel = valorPapel.divide(
					new BigDecimal(quantidadeFolhasParaPapelInterno)).setScale(
					2, RoundingMode.HALF_UP);
			o.addItem(o.new Item(String.format("Papel Interno %s",
					papelInterno.toString()), valorPapel));
		}
	}

	public void calcularValorImpressaoEnvelope(Orcamento o) {
		if (impressaoEnvelope != null) {
			BigDecimal precoVenda = impressaoEnvelope.getPrecoVenda();
			o.addItem(o.new Item(impressaoEnvelope.toString(), precoVenda));
		}
	}

	public void calcularValorImpressaoInterno(Orcamento o) {
		if (impressaoInterno != null) {
			BigDecimal precoVenda = impressaoInterno.getPrecoVenda();
			o.addItem(o.new Item(impressaoInterno.toString(), precoVenda));
		}
	}

	public void calcularValorFita(Orcamento o) {
		if (fita != null) {
			o.addItem(o.new Item(fita.toString(), fita.getPrecoVenda(modelo)));
		}
	}

	public void calcularImpressaoNome(Orcamento o) {
		if (impressaoNome != null) {
			o.addItem(o.new Item(impressaoNome.toString(), impressaoNome
					.getPrecoVenda()));
		}
	}

	public void calcularRenda(Orcamento o) {
		if (renda != null) {
			o.addItem(o.new Item(renda.toString(), renda.getPrecoVenda(modelo)));
		}
	}

	public void calcularAplicacaoSerigrafiaInterno(Orcamento o) {
		if (serigrafiaInterno != null) {
			o.addItem(o.new Item(serigrafiaInterno.toString(),
					serigrafiaInterno.getPrecoVenda()));
		}
	}

	public void calcularAplicacaoSerigrafiaEnvelope(Orcamento o) {
		if (serigrafiaEnvelope != null) {
			o.addItem(o.new Item(serigrafiaEnvelope.toString(),
					serigrafiaEnvelope.getPrecoVenda()));
		}
	}

	public void calcularHotStamping(Orcamento o) {
		if (hotStamp != null) {
			o.addItem(o.new Item(hotStamp.toString(), hotStamp.getPrecoVenda()));
		}
	}

	public void calcularAplicacaoStrass(Orcamento o) {
		if (quantidadeStrass > 0) {
			o.addItem(o.new Item(strass.toString(), strass
					.getPrecoVenda(quantidadeStrass)));
		}
	}

	public void calcularFechamentoIma(Orcamento o) {
		if (ima != null) {
			o.addItem(o.new Item(ima.toString(), ima.getPrecoVenda()));
		}
	}

	public void calcularPrecoCorte(Orcamento o) {
		if (corte != null) {
			o.addItem(o.new Item(corte.toString(), corte
					.getPrecoVenda(quantidadeConvites)));
		}
	}

	public void calcularEmbalagem(Orcamento o) {
		if (this.embalagem != null) {
			o.addItem(o.new Item(embalagem.toString(), embalagem
					.getPrecoVenda()));
			;
		}
	}

	public class Orcamento {

		private int quantidade;

		private List<Item> items = new ArrayList<>();

		public List<Item> getItems() {
			return items;
		}

		public int getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}

		public void addItem(Item item) {
			this.items.add(item);
		}

		public BigDecimal getPrecoFinal() {
			return this.items.stream().parallel().map(item -> {
				return item.valor;
			}).reduce((x, y) -> {
				return x.add(y);
			}).get();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();

			items.forEach(item -> {
				sb.append(item.toString()).append(
						System.getProperty("line.separator"));
			});
			sb.append("Preço final individual: " + getPrecoFinal());
			return sb.toString();
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

			@Override
			public String toString() {
				return String.format("Item orçado: %s - Valor: %s", item,
						valor.toPlainString());
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
		private Colagem colagem;
		private CorteEnvelope corte;

		private CalculadoraBuilder() {

		}

		public static CalculadoraBuilder getInstance() {
			return new CalculadoraBuilder();
		}

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

		public CalculadoraBuilder colagem(Colagem colagem) {
			this.colagem = colagem;
			return this;
		}

		public CalculadoraBuilder corte(CorteEnvelope corte) {
			this.corte = corte;
			return this;
		}

		public Calculadora build() {

			// if (corte == null) {
			// throw new CorteInvalidoException();
			// }

			if (modelo == null) {
				throw new ModeloInvalidoException();
			}

			if (papelEnvelope == null) {
				throw new PapelInvalidoException();
			}

			return new Calculadora(quantidadeConvites, modelo, colagem,
					papelEnvelope, papelInterno, impressaoEnvelope,
					impressaoInterno, fita, laco, hotStamp, serigrafiaEnvelope,
					serigrafiaInterno, renda, ima, quantidadeStrass, strass,
					impressaoNome, corte);

		}
	}

}
