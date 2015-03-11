package com.rp.hd.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.rp.hd.domain.exceptions.ModeloInvalidoException;
import com.rp.hd.domain.exceptions.PapelInvalidoException;

public class Calculadora {

	private static final int PRAZO_MAXIMO_PARCELAMENTO = 6;
	private static final BigDecimal CEM = new BigDecimal(100);
	private static final Locale BR = new Locale("pt","BR");
	private static final NumberFormat NF = NumberFormat.getCurrencyInstance(BR);

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
	private final Cliche cliche;
	private BigDecimal taxaAdministracaoCartaoCredito;
	private BigDecimal taxaAdministracaoCartaoDebito;
	private BigDecimal taxaJurosMensal;

	private BigDecimal custoUnidade = BigDecimal.ZERO;
	private BigDecimal custoOutros = BigDecimal.ZERO;

	private Calculadora(int quantidadeConvites, ModeloConvite modelo,
			Colagem colagem, Papel papelEnvelope, Papel papelInterno,
			Impressao impressaoEnvelope, Impressao impressaoInterno, Fita fita,
			Laco laco, HotStamp hotStamp, Serigrafia serigrafiaEnvelope,
			Serigrafia serigrafiaInterno, Renda renda, Ima ima,
			int quantidadeStrass, Strass strass, ImpressaoNome impressaoNome,
			CorteEnvelope corte, Cliche cliche,
			BigDecimal taxaAdministracaoCartaoCredito,
			BigDecimal taxaAdministracaoCartaoDebito, BigDecimal taxaJurosMensal) {
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
		this.cliche = cliche;
		this.taxaAdministracaoCartaoCredito = taxaAdministracaoCartaoCredito;
		this.taxaAdministracaoCartaoDebito = taxaAdministracaoCartaoDebito;
		this.taxaJurosMensal = taxaJurosMensal;
	}

	public Orcamento calcular() {
		Orcamento o = new Orcamento(taxaAdministracaoCartaoCredito,
				taxaAdministracaoCartaoDebito, taxaJurosMensal);
		o.setQuantidade(this.quantidadeConvites);
		calcularPrecoModeloConvite(o);
		calcularPapelInterno(o);
		calcularValorImpressaoEnvelope(o);
		calcularValorImpressaoInterno(o);
		calcularValorFita(o);
		calculaValorLaco(o);
		calcularImpressaoNome(o);
		calcularRenda(o);
		calcularAplicacaoSerigrafiaInterno(o);
		calcularAplicacaoSerigrafiaEnvelope(o);
		calcularHotStamping(o);
		calcularAplicacaoStrass(o);
		calcularPrecoCorte(o);
		calcularEmbalagem(o);
		calcularCliche(o);
		calcularFechamentoIma(o);
		return o;
	}

	private void atualizaCustoUnidade(Orcamento o, BigDecimal valor) {
		o.setCustoUnidade(valor);
	}

	private void atualizaCustoOutros(Orcamento o, BigDecimal valor) {
		o.setCustoOutros(valor);
	}

	public void calcularCliche(Orcamento o) {
		if (this.cliche != null) {

			custoOutros = custoOutros.add(this.cliche.getCusto());
			atualizaCustoOutros(o, custoOutros);

			BigDecimal valor = this.cliche.getValorVenda();
			o.addItemValortotal(o.new Item(this.cliche.toString(), valor));
		}
	}

	public void calcularPrecoModeloConvite(Orcamento o) {
		custoUnidade = custoUnidade.add(this.modelo.getCustoAtual(
				papelEnvelope, colagem));
		atualizaCustoUnidade(o, custoUnidade);
		BigDecimal valor = this.modelo.getPrecoVenda(papelEnvelope, colagem);
		o.addItem(o.new Item(this.modelo.toString(), valor));
	}

	public void calcularPapelInterno(Orcamento o) {
		int quantidadeFolhasParaPapelInterno = modelo.getTamanhoItemInterno();
		if (papelInterno != null && quantidadeFolhasParaPapelInterno > 0) {

			custoUnidade = custoUnidade.add(this.papelInterno
					.getCustoAtual()
					.divide(new BigDecimal(quantidadeFolhasParaPapelInterno),
							4, RoundingMode.HALF_UP)
					.setScale(2, RoundingMode.HALF_UP));
			atualizaCustoUnidade(o, custoUnidade);

			BigDecimal valorPapel = this.papelInterno.getPrecoAtual();
			valorPapel = valorPapel.divide(
					new BigDecimal(quantidadeFolhasParaPapelInterno), 4,
					RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);
			o.addItem(o.new Item(String.format("Papel Interno %s",
					papelInterno.toString()), valorPapel));
		}
	}

	public void calcularValorImpressaoEnvelope(Orcamento o) {
		if (impressaoEnvelope != null) {
			custoUnidade = custoUnidade.add(impressaoEnvelope.getCustoAtual());
			atualizaCustoUnidade(o, custoUnidade);

			BigDecimal precoVenda = impressaoEnvelope.getPrecoVenda();
			o.addItem(o.new Item(impressaoEnvelope.toString(), precoVenda));
		}
	}

	public void calcularValorImpressaoInterno(Orcamento o) {
		if (impressaoInterno != null) {

			custoUnidade = custoUnidade.add(impressaoInterno.getCustoAtual());
			atualizaCustoUnidade(o, custoUnidade);

			BigDecimal precoVenda = impressaoInterno.getPrecoVenda();
			o.addItem(o.new Item(impressaoInterno.toString(), precoVenda));
		}
	}

	public void calcularValorFita(Orcamento o) {
		if (fita != null) {
			custoUnidade = custoUnidade.add(fita.getPrecoCusto(modelo));
			atualizaCustoUnidade(o, custoUnidade);
			o.addItem(o.new Item(fita.toString(), fita.getPrecoVenda(modelo)));
		}
	}

	public void calculaValorLaco(Orcamento o) {
		if (laco != null) {
			custoUnidade = custoUnidade.add(laco.getCusto(fita));
			atualizaCustoUnidade(o, custoUnidade);
			o.addItem(o.new Item(laco.toString(), laco.getPrecoVenda(fita)));
		}
	}

	public void calcularImpressaoNome(Orcamento o) {
		if (impressaoNome != null) {
			custoUnidade = custoUnidade.add(impressaoNome.getPrecoCusto());
			atualizaCustoUnidade(o, custoUnidade);
			o.addItem(o.new Item(impressaoNome.toString(), impressaoNome
					.getPrecoVenda()));
		}
	}

	public void calcularRenda(Orcamento o) {
		if (renda != null) {
			custoUnidade = custoUnidade.add(renda.getPrecoCusto(modelo));
			atualizaCustoUnidade(o, custoUnidade);
			o.addItem(o.new Item(renda.toString(), renda.getPrecoVenda(modelo)));
		}
	}

	public void calcularAplicacaoSerigrafiaInterno(Orcamento o) {
		if (serigrafiaInterno != null) {
			custoUnidade = custoUnidade.add(serigrafiaInterno.getCusto());
			atualizaCustoUnidade(o, custoUnidade);
			o.addItem(o.new Item(serigrafiaInterno.toString(),
					serigrafiaInterno.getPrecoVenda()));
		}
	}

	public void calcularAplicacaoSerigrafiaEnvelope(Orcamento o) {
		if (serigrafiaEnvelope != null) {
			custoUnidade = custoUnidade.add(serigrafiaEnvelope.getCusto());
			atualizaCustoUnidade(o, custoUnidade);
			o.addItem(o.new Item(serigrafiaEnvelope.toString(),
					serigrafiaEnvelope.getPrecoVenda()));
		}
	}

	public void calcularHotStamping(Orcamento o) {
		if (hotStamp != null) {

			custoOutros = custoOutros.add(hotStamp.getPrecoCusto());
			atualizaCustoOutros(o, custoOutros);
			o.addItemValortotal(o.new Item(hotStamp.toString(), hotStamp
					.getPrecoVenda()));
		}
	}

	public void calcularAplicacaoStrass(Orcamento o) {
		if (quantidadeStrass > 0) {
			custoUnidade = custoUnidade.add(strass
					.getPrecoCusto(quantidadeStrass));
			atualizaCustoUnidade(o, custoUnidade);
			o.addItem(o.new Item(strass.toString(), strass
					.getPrecoVenda(quantidadeStrass)));
		}
	}

	public void calcularFechamentoIma(Orcamento o) {
		if (ima != null) {

			custoUnidade = custoUnidade.add(ima.getCusto());
			atualizaCustoUnidade(o, custoUnidade);

			o.addItem(o.new Item(ima.toString(), ima.getPrecoVenda()));
		}
	}

	public void calcularPrecoCorte(Orcamento o) {
		if (corte != null) {

			custoUnidade = custoUnidade.add(corte.getCusto(quantidadeConvites));
			atualizaCustoUnidade(o, custoUnidade);

			o.addItem(o.new Item(corte.toString(), corte
					.getPrecoVenda(quantidadeConvites)));
		}
	}

	public void calcularEmbalagem(Orcamento o) {
		if (this.embalagem != null) {

			custoUnidade = custoUnidade.add(embalagem.getCustoAtual());
			atualizaCustoUnidade(o, custoUnidade);
			o.addItem(o.new Item(embalagem.toString(), embalagem
					.getPrecoVenda()));
			;
		}
	}

	public class Orcamento {

		private final BigDecimal taxaAdministracaoCartaoCredito;
		private final BigDecimal taxaAdministracaoCartaoDebito;
		private final BigDecimal taxaJurosMensal;

		private int quantidade;

		private BigDecimal valorUnidade;
		private BigDecimal valorTotalConvites;
		private BigDecimal valorItemsPorPedido;
		private BigDecimal valorTotal;

		private BigDecimal valorUnidadePrazo;
		private BigDecimal valorTotalConvitesPrazo;
		private BigDecimal valorItemsPorPedidoPrazo;
		private BigDecimal valorTotalPrazo;

		private BigDecimal custoUnidade;
		private BigDecimal custoOutros;

		private List<Item> itemsCompoePrecoUnidade = new ArrayList<>();
		private List<Item> itensCompoePrecoPorCompra = new ArrayList<Calculadora.Orcamento.Item>();

		public Orcamento(BigDecimal taxaAdministracaoCartaoCredito,
				BigDecimal taxaAdministracaoCartaoDebito,
				BigDecimal taxaJurosMensal) {
			this.taxaAdministracaoCartaoCredito = taxaAdministracaoCartaoCredito;
			this.taxaAdministracaoCartaoDebito = taxaAdministracaoCartaoDebito;
			this.taxaJurosMensal = taxaJurosMensal;
		}

		public List<Item> getItems() {
			return itemsCompoePrecoUnidade;
		}

		public int getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}

		public void addItem(Item item) {
			this.itemsCompoePrecoUnidade.add(item);
		}

		public void addItemValortotal(Item item) {
			this.itensCompoePrecoPorCompra.add(item);
		}

		public BigDecimal getValorUnidade() {
			BigDecimal valorParcial = this.itemsCompoePrecoUnidade.stream()
					.parallel().map(item -> {
						return item.valor;
					}).reduce((x, y) -> {
						return x.add(y);
					}).get().multiply(new BigDecimal("1.1"))
					.setScale(2, RoundingMode.HALF_UP);

			valorParcial = adicionaTaxaCartaoCredito(valorParcial);

			return arredondaValorUnidadeParaCima(valorParcial);
		}

		private BigDecimal adicionaTaxaCartaoCredito(BigDecimal valor) {
			BigDecimal taxa = BigDecimal.ONE.add(taxaAdministracaoCartaoCredito
					.divide(CEM));
			return valor.multiply(taxa);
		}

		private BigDecimal arredondaValorUnidadeParaCima(BigDecimal valor) {
			return valor.setScale(1, RoundingMode.UP);
		}

		public BigDecimal getValorTotalConvites() {
			return getValorUnidade().multiply(new BigDecimal(quantidade));
		}

		public BigDecimal getValorItemsPorPedido() {
			BigDecimal valorTotal = BigDecimal.ZERO;
			if (!itensCompoePrecoPorCompra.isEmpty()) {
				valorTotal = itensCompoePrecoPorCompra.stream().parallel()
						.map(item -> {
							return item.valor;
						}).reduce((x, y) -> {
							return x.add(y);
						}).get().setScale(2, RoundingMode.HALF_UP);
			}

			valorTotal = adicionaTaxaCartaoCredito(valorTotal);

			valorTotal = arredondaValorUnidadeParaCima(valorTotal);

			return valorTotal;
		}

		public BigDecimal getValorTotal() {
			BigDecimal valorTotalUnidade = getValorUnidade().multiply(
					new BigDecimal(quantidadeConvites));
			BigDecimal valorTotalItemsPorPedido = getValorItemsPorPedido();
			return valorTotalUnidade.add(valorTotalItemsPorPedido).setScale(2,
					RoundingMode.HALF_UP);
		}

		public BigDecimal getValorUnidadePrazo() {
			BigDecimal valor = getValorUnidade();
			if (taxaJurosMensal != null) {
				BigDecimal juros = BigDecimal.ONE.add(
						taxaJurosMensal.divide(CEM)).pow(
						PRAZO_MAXIMO_PARCELAMENTO);
				valor = valor.multiply(juros);
			}
			return arredondaValorUnidadeParaCima(valor);
		}

		public BigDecimal getValorTotalConvitesPrazo() {
			return getValorUnidadePrazo().multiply(
					new BigDecimal(quantidadeConvites));
		}

		public BigDecimal getValorTotalPrazo() {
			return getValorTotalConvitesPrazo().add(
					getValorItemsPorPedidoPrazo());
		}

		public BigDecimal getValorItemsPorPedidoPrazo() {
			BigDecimal valor = getValorItemsPorPedido();
			if (taxaJurosMensal != null) {
				BigDecimal juros = BigDecimal.ONE.add(
						taxaJurosMensal.divide(CEM)).pow(
						PRAZO_MAXIMO_PARCELAMENTO);
				valor = valor.multiply(juros);
			}
			return arredondaValorUnidadeParaCima(valor);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();

			itemsCompoePrecoUnidade.forEach(item -> {
				sb.append(item.toString()).append(
						System.getProperty("line.separator"));
			});
			sb.append("Preço final individual: " + getValorUnidade());
			return sb.toString();
		}

		public BigDecimal getCustoUnidade() {
			return custoUnidade;
		}

		public void setCustoUnidade(BigDecimal custoUnidade) {
			this.custoUnidade = custoUnidade;
		}

		public BigDecimal getCustoOutros() {
			return custoOutros;
		}

		public void setCustoOutros(BigDecimal custoOutros) {
			this.custoOutros = custoOutros;
		}

		public List<OpcaoParcelamento> getOpcoesParcelamento() {
			BigDecimal valorTotal = getValorTotalPrazo();
			List<OpcaoParcelamento> resultado = new ArrayList<>();
			for (int i = 2; i <= PRAZO_MAXIMO_PARCELAMENTO; i++) {
				BigDecimal valorParcela = valorTotal.divide(new BigDecimal(i), 2, RoundingMode.HALF_UP);
				resultado.add(new OpcaoParcelamento(i, valorParcela));
			}
			return resultado;
		}

		public class OpcaoParcelamento {
			private final int quantidadeParcelas;
			private final BigDecimal valorParcela;

			public OpcaoParcelamento(int quantidadeParcelas,
					BigDecimal valorParcela) {
				this.quantidadeParcelas = quantidadeParcelas;
				this.valorParcela = valorParcela;
			}

			public int getQuantidadeParcelas() {
				return quantidadeParcelas;
			}

			public BigDecimal getValorParcela() {
				return valorParcela;
			}
			
			public String getValorParcelaMoeda() {
				return NF.format(getValorParcela());
			}

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
		private Colagem colagem;
		private CorteEnvelope corte;
		private Cliche cliche;
		private BigDecimal taxaAdministracaoCartaoCredito;
		private BigDecimal taxaAdministracaoCartaoDebito;
		private BigDecimal taxaJurosMensal;

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

		public CalculadoraBuilder colagem(Colagem colagem) {
			this.colagem = colagem;
			return this;
		}

		public CalculadoraBuilder corte(CorteEnvelope corte) {
			this.corte = corte;
			return this;
		}

		public CalculadoraBuilder cliche(Cliche cliche) {
			this.cliche = cliche;
			return this;
		}

		public CalculadoraBuilder taxaAdministracaoCartaoCredito(
				BigDecimal taxaAdministracaoCartaoCredito) {
			this.taxaAdministracaoCartaoCredito = taxaAdministracaoCartaoCredito;
			return this;
		}

		public CalculadoraBuilder taxaAdministracaoCartaoDebito(
				BigDecimal taxaAdministracaoCartaoDebito) {
			this.taxaAdministracaoCartaoDebito = taxaAdministracaoCartaoDebito;
			return this;
		}

		public CalculadoraBuilder taxaJurosMensal(BigDecimal taxaJurosMensal) {
			this.taxaJurosMensal = taxaJurosMensal;
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
					impressaoNome, corte, cliche,
					taxaAdministracaoCartaoCredito,
					taxaAdministracaoCartaoDebito, taxaJurosMensal);

		}
	}

}
