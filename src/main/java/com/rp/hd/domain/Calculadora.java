package com.rp.hd.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.rp.hd.domain.exceptions.ModeloInvalidoException;
import com.rp.hd.domain.exceptions.PapelInvalidoException;

public class Calculadora {

	private static final int PRAZO_MAXIMO_PARCELAMENTO = 3;
	private static final BigDecimal CEM = new BigDecimal(100);
	private static final Locale BR = new Locale("pt", "BR");
	private static final NumberFormat NF = NumberFormat.getCurrencyInstance(BR);

	private final int quantidadeConvites;
	private final ModeloConvite modelo;
	private final Colagem colagem;
	private final Papel papelEnvelope;
	private final Papel papelInterno;
	private final Papel papelRevestimentoInterno;
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
	private final CorteEnvelope corteInternoAlmofadado;
	private final Acoplamento acoplamentoEnvelope;
	private final Acoplamento acoplamentoInterno;
	private final Cliche cliche;
	private BigDecimal taxaAdministracaoCartaoCredito;
	private BigDecimal taxaAdministracaoCartaoDebito;
	private BigDecimal taxaJurosMensal;
	private List<MaoObra> custoMaoObra;

	private BigDecimal custoUnidade = BigDecimal.ZERO;
	private BigDecimal custoOutros = BigDecimal.ZERO;
	private int tempoMedioPorUnidade = 0;

	private Calculadora(int quantidadeConvites, ModeloConvite modelo,
			Colagem colagem, Papel papelEnvelope, Papel papelInterno,
			Impressao impressaoEnvelope, Impressao impressaoInterno, Fita fita,
			Laco laco, HotStamp hotStamp, Serigrafia serigrafiaEnvelope,
			Serigrafia serigrafiaInterno, Renda renda, Ima ima,
			int quantidadeStrass, Strass strass, ImpressaoNome impressaoNome,
			CorteEnvelope corte, CorteEnvelope corteInternoAlmofadado,
			Cliche cliche, BigDecimal taxaAdministracaoCartaoCredito,
			BigDecimal taxaAdministracaoCartaoDebito,
			BigDecimal taxaJurosMensal, Acoplamento acoplamentoEnvelope,
			Acoplamento acoplamentoInterno, Papel papelRevestimentoInterno,
			List<MaoObra> custoMaoObra) {
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
		this.corteInternoAlmofadado = corteInternoAlmofadado;
		this.acoplamentoEnvelope = acoplamentoEnvelope;
		this.acoplamentoInterno = acoplamentoInterno;
		this.papelRevestimentoInterno = papelRevestimentoInterno;
		this.custoMaoObra = custoMaoObra;
	}

	public Orcamento calcular() {
		Orcamento o = new Orcamento(taxaAdministracaoCartaoCredito,
				taxaAdministracaoCartaoDebito, taxaJurosMensal);
		o.setQuantidade(this.quantidadeConvites);
		calcularPrecoModeloConvite(o);
		calcularPapelInterno(o);
		calcularPapelRevestimentoInterno(o);
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
		calcularPrecoCorteInternoAlmofadado(o);
		calcularPrecoAcoplamentoEvelope(o);
		calcularPrecoAcoplamentoInterno(o);
		calcularEmbalagem(o);
		calcularCliche(o);
		calcularFechamentoIma(o);
		return o;
	}

	private Optional<MaoObra> getCustoMaoObra(TipoMaoObra tipo) {
		return custoMaoObra.stream().filter(custo -> {
			return custo.getTipoMaoObra().equals(tipo);
		}).findFirst();
	}

	private void atualizaCustoUnidade(Orcamento o, BigDecimal valor, int tempoMedioEstimado) {
		o.setCustoUnidade(valor);
		o.setTempoEstimado(tempoMedioEstimado);
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
		custoUnidade = custoUnidade.add(this.modelo
				.getCustoAtual(papelEnvelope));

		BigDecimal valorMaoObraSeparacaoPapelParaCorte = BigDecimal.ZERO;
		Optional<MaoObra> custoMaoObraSeparacaoPapel = getCustoMaoObra(TipoMaoObra.SEPARACAO_PAPEL_PARA_CORTE);
		if (custoMaoObraSeparacaoPapel.isPresent()) {
			custoUnidade = custoUnidade.add(custoMaoObraSeparacaoPapel.get()
					.getCusto());
			valorMaoObraSeparacaoPapelParaCorte = valorMaoObraSeparacaoPapelParaCorte
					.add(custoMaoObraSeparacaoPapel.get().getValorVenda());
			tempoMedioPorUnidade += custoMaoObraSeparacaoPapel.get()
					.getTempoMedioPorUnidade();
		}

		BigDecimal valorVendaMaoObraDobragemEnvelope = BigDecimal.ZERO;
		Optional<MaoObra> custoMaoObraDobragem = getCustoMaoObra(TipoMaoObra.DOBRAGEM_ENVELOPE);
		if (custoMaoObraDobragem.isPresent()) {
			custoUnidade = custoUnidade.add(custoMaoObraDobragem.get()
					.getCusto()
					.multiply(new BigDecimal(modelo.getQuantidadeDobras())));
			valorVendaMaoObraDobragemEnvelope = valorVendaMaoObraDobragemEnvelope
					.add(custoMaoObraDobragem
							.get()
							.getValorVenda()
							.multiply(
									new BigDecimal(modelo.getQuantidadeDobras())));
			tempoMedioPorUnidade += custoMaoObraDobragem.get()
					.getTempoMedioPorUnidade() * modelo.getQuantidadeDobras();
		}

		if (modelo.isTemColagem()) {
			Optional<MaoObra> custoColagem = getCustoMaoObra(TipoMaoObra.COLAGEM_ENVELOPE);
			if (custoColagem.isPresent()) {
				custoUnidade.add(custoColagem.get().getCusto());
				valorVendaMaoObraDobragemEnvelope = valorVendaMaoObraDobragemEnvelope
						.add(custoColagem.get().getValorVenda());
				tempoMedioPorUnidade += custoColagem.get()
						.getTempoMedioPorUnidade();
			}
		}

		atualizaCustoUnidade(o, custoUnidade, tempoMedioPorUnidade);

		BigDecimal valor = this.modelo.getPrecoVenda(papelEnvelope);
		valor = valor.add(valorVendaMaoObraDobragemEnvelope).add(
				valorMaoObraSeparacaoPapelParaCorte);

		o.addItem(o.new Item(this.modelo.toString(), valor));
	}

	public void calcularPapelInterno(Orcamento o) {
		int quantidadeFolhasParaPapelInterno = modelo.getTamanhoItemInterno();
		if (papelInterno != null && quantidadeFolhasParaPapelInterno > 0) {

			Optional<MaoObra> custoMaoObraCortePapelInterno = getCustoMaoObra(TipoMaoObra.CORTE_PAPEL_INTERNO);
			Optional<MaoObra> custoMaoObraEncartePapelInterno = getCustoMaoObra(TipoMaoObra.ENCARTE);
			BigDecimal precoVendaMaoObra = BigDecimal.ZERO;

			custoUnidade = custoUnidade.add(this.papelInterno
					.getCustoAtual()
					.divide(new BigDecimal(quantidadeFolhasParaPapelInterno),
							4, RoundingMode.HALF_UP)
					.setScale(2, RoundingMode.HALF_UP));

			if (custoMaoObraCortePapelInterno.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraCortePapelInterno
						.get().getCusto());
				precoVendaMaoObra = precoVendaMaoObra
						.add(custoMaoObraCortePapelInterno.get()
								.getValorVenda());

				tempoMedioPorUnidade += custoMaoObraCortePapelInterno.get()
						.getTempoMedioPorUnidade();

			}

			if (custoMaoObraEncartePapelInterno.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraEncartePapelInterno
						.get().getCusto());
				precoVendaMaoObra = precoVendaMaoObra
						.add(custoMaoObraEncartePapelInterno.get()
								.getValorVenda());

				tempoMedioPorUnidade += custoMaoObraEncartePapelInterno.get()
						.getTempoMedioPorUnidade();
			}

			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

			BigDecimal valorPapel = this.papelInterno.getPrecoAtual();
			valorPapel = valorPapel.divide(
					new BigDecimal(quantidadeFolhasParaPapelInterno), 4,
					RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);

			valorPapel = valorPapel.add(precoVendaMaoObra);

			o.addItem(o.new Item(String.format("Papel Interno %s",
					papelInterno.toString()), valorPapel));
		}
	}

	public void calcularPapelRevestimentoInterno(Orcamento o) {
		Integer quantidadeFolhasRevestimentoIterno = modelo
				.getQuantidadeFolhasParaRevestimento();
		if (papelRevestimentoInterno != null
				&& (quantidadeFolhasRevestimentoIterno != null && quantidadeFolhasRevestimentoIterno > 0)) {

			Optional<MaoObra> valorCustoMaoObraRevestimento = getCustoMaoObra(TipoMaoObra.COLOCAO_REVESTIMENTO_INTERNO);
			BigDecimal valorMaoObraRevestimentoVenda = BigDecimal.ZERO;

			custoUnidade = custoUnidade.add(this.papelRevestimentoInterno
					.getCustoAtual()
					.divide(new BigDecimal(quantidadeFolhasRevestimentoIterno),
							4, RoundingMode.HALF_UP)
					.setScale(2, RoundingMode.HALF_UP));

			if (valorCustoMaoObraRevestimento.isPresent()) {
				custoUnidade = custoUnidade.add(valorCustoMaoObraRevestimento
						.get().getCusto());
				valorMaoObraRevestimentoVenda = valorCustoMaoObraRevestimento
						.get().getValorVenda();

				tempoMedioPorUnidade += valorCustoMaoObraRevestimento.get()
						.getTempoMedioPorUnidade();

			}

			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

			BigDecimal valorCorteUnidade = BigDecimal.ZERO;
			if (corte != null) {
				custoUnidade = custoUnidade.add(corte
						.getCusto(quantidadeConvites));
				atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

				valorCorteUnidade = corte.getPrecoVenda(quantidadeConvites);

				o.addItem(o.new Item("Corte Revestimento Interno do Envelope",
						valorCorteUnidade));
			}

			BigDecimal valorColagem = BigDecimal.ZERO;
			if (colagem != null) {
				valorColagem = colagem != null ? colagem.getPrecoAtual()
						: BigDecimal.ZERO;

				o.addItem(o.new Item(
						"Colagem do Revestimento Interno do Envelope",
						valorColagem));
			}

			BigDecimal valorPapel = this.papelRevestimentoInterno
					.getPrecoAtual();
			valorPapel = valorPapel.divide(
					new BigDecimal(quantidadeFolhasRevestimentoIterno), 4,
					RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);

			valorPapel = valorPapel.add(valorCorteUnidade).add(valorColagem)
					.add(valorMaoObraRevestimentoVenda);

			o.addItem(o.new Item(String.format("Papel Revestimento Interno %s",
					papelRevestimentoInterno.toString()), valorPapel));
		}
	}

	public void calcularValorImpressaoEnvelope(Orcamento o) {
		if (impressaoEnvelope != null) {
			custoUnidade = custoUnidade.add(impressaoEnvelope.getCustoAtual());

			BigDecimal valorMaoObraImpressaoEnvelopeVenda = BigDecimal.ZERO;

			Optional<MaoObra> custoMaoObraImpressao = getCustoMaoObra(TipoMaoObra.IMPRESSAO_ENVELOPE);
			if (custoMaoObraImpressao.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraImpressao.get()
						.getCusto());
				valorMaoObraImpressaoEnvelopeVenda = custoMaoObraImpressao
						.get().getValorVenda();

				tempoMedioPorUnidade += custoMaoObraImpressao.get()
						.getTempoMedioPorUnidade();

			}

			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

			BigDecimal precoVenda = impressaoEnvelope.getPrecoVenda();
			precoVenda = precoVenda.add(valorMaoObraImpressaoEnvelopeVenda);
			o.addItem(o.new Item(impressaoEnvelope.toString(), precoVenda));
		}
	}

	public void calcularValorImpressaoInterno(Orcamento o) {
		if (impressaoInterno != null) {
			BigDecimal valorMaoObraImpressaoInterno = BigDecimal.ZERO;
			Optional<MaoObra> custoMaoObraImpressaoInterno = getCustoMaoObra(TipoMaoObra.IMPRESSAO_INTERNO);
			if (custoMaoObraImpressaoInterno.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraImpressaoInterno
						.get().getCusto());
				valorMaoObraImpressaoInterno = custoMaoObraImpressaoInterno
						.get().getValorVenda();

				tempoMedioPorUnidade += custoMaoObraImpressaoInterno.get()
						.getTempoMedioPorUnidade();
			}

			custoUnidade = custoUnidade.add(impressaoInterno.getCustoAtual());
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

			BigDecimal precoVenda = impressaoInterno.getPrecoVenda();
			precoVenda = precoVenda.add(valorMaoObraImpressaoInterno);
			o.addItem(o.new Item(impressaoInterno.toString(), precoVenda));
		}
	}

	public void calcularValorFita(Orcamento o) {
		if (fita != null) {

			BigDecimal valorMaoObraCorteFita = BigDecimal.ZERO;
			Optional<MaoObra> custoCorteFita = getCustoMaoObra(TipoMaoObra.CORTE_FITA);
			if (custoCorteFita.isPresent()) {
				custoUnidade = custoUnidade
						.add(custoCorteFita.get().getCusto());
				valorMaoObraCorteFita = custoCorteFita.get().getValorVenda();
			}

			BigDecimal valorMaoObraFitaVenda = BigDecimal.ZERO;
			Optional<MaoObra> custoMaoObraFita = getCustoMaoObra(TipoMaoObra.COLOCACAO_FITA_CONTORNO);
			if (custoMaoObraFita.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraFita.get()
						.getCusto());
				valorMaoObraFitaVenda = custoMaoObraFita.get().getValorVenda();

				tempoMedioPorUnidade += custoMaoObraFita.get()
						.getTempoMedioPorUnidade();
			}

			custoUnidade = custoUnidade.add(fita.getPrecoCusto(modelo));
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);
			o.addItem(o.new Item(fita.toString(), fita.getPrecoVenda(modelo)
					.add(valorMaoObraFitaVenda).add(valorMaoObraCorteFita)));
		}
	}

	public void calculaValorLaco(Orcamento o) {
		if (laco != null) {

			Optional<MaoObra> custoMaoObraConfeccaoLaco = getCustoMaoObra(TipoMaoObra.CONFECCAO_LACO);
			Optional<MaoObra> custoMaoObraColocacaoLaco = getCustoMaoObra(TipoMaoObra.COLOCACAO_LACO);
			BigDecimal valorVendaMaoObraConfeccaoLaco = BigDecimal.ZERO;
			BigDecimal valorVendaMaoObraColoocacaoLaco = BigDecimal.ZERO;
			if (custoMaoObraConfeccaoLaco.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraConfeccaoLaco.get()
						.getCusto());
				valorVendaMaoObraConfeccaoLaco = custoMaoObraConfeccaoLaco
						.get().getValorVenda();

				tempoMedioPorUnidade += custoMaoObraConfeccaoLaco.get()
						.getTempoMedioPorUnidade();

			}
			if (custoMaoObraColocacaoLaco.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraColocacaoLaco.get()
						.getCusto());
				valorVendaMaoObraColoocacaoLaco = custoMaoObraColocacaoLaco
						.get().getValorVenda();

				tempoMedioPorUnidade += custoMaoObraColocacaoLaco.get()
						.getTempoMedioPorUnidade();

			}

			custoUnidade = custoUnidade.add(laco.getCusto(fita));

			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

			BigDecimal precoVenda = laco.getPrecoVenda(fita);
			precoVenda = precoVenda.add(valorVendaMaoObraConfeccaoLaco).add(
					valorVendaMaoObraColoocacaoLaco);
			o.addItem(o.new Item(laco.toString(), precoVenda));
		}
	}

	public void calcularImpressaoNome(Orcamento o) {
		if (impressaoNome != null) {

			Optional<MaoObra> maoObraImpressaoNome = getCustoMaoObra(TipoMaoObra.IMPRESSAO_NOME_CONVIDADOS);
			BigDecimal precoVendaMaoObraImpressaoNome = BigDecimal.ZERO;
			if (maoObraImpressaoNome.isPresent()) {
				custoUnidade = custoUnidade.add(maoObraImpressaoNome.get()
						.getCusto());
				precoVendaMaoObraImpressaoNome = maoObraImpressaoNome.get()
						.getValorVenda();

				tempoMedioPorUnidade += maoObraImpressaoNome.get()
						.getTempoMedioPorUnidade();
			}

			Optional<MaoObra> maoObraColocacaoNome = getCustoMaoObra(TipoMaoObra.COLOCAO_NOMES_CONVIDADOS);
			BigDecimal precoVendaColocacaoNome = BigDecimal.ZERO;
			if (maoObraColocacaoNome.isPresent()) {
				custoUnidade = custoUnidade.add(maoObraColocacaoNome.get()
						.getCusto());
				precoVendaColocacaoNome = maoObraColocacaoNome.get()
						.getValorVenda();

				tempoMedioPorUnidade += maoObraColocacaoNome.get()
						.getTempoMedioPorUnidade();

			}

			custoUnidade = custoUnidade.add(impressaoNome.getPrecoCusto());
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);
			BigDecimal precoVenda = impressaoNome.getPrecoVenda()
					.add(precoVendaMaoObraImpressaoNome)
					.add(precoVendaColocacaoNome);
			o.addItem(o.new Item(impressaoNome.toString(), precoVenda));
		}
	}

	public void calcularRenda(Orcamento o) {
		if (renda != null) {

			Optional<MaoObra> custoMaoObraCorteRenda = getCustoMaoObra(TipoMaoObra.CORTE_RENDA);
			BigDecimal precoMaoObraCorteRenda = BigDecimal.ZERO;
			if (custoMaoObraCorteRenda.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraCorteRenda.get()
						.getCusto());
				precoMaoObraCorteRenda = custoMaoObraCorteRenda.get()
						.getValorVenda();

				tempoMedioPorUnidade += custoMaoObraCorteRenda.get()
						.getTempoMedioPorUnidade();

			}

			Optional<MaoObra> custoMaoObraRenda = getCustoMaoObra(TipoMaoObra.COLOCACAO_RENDA);
			BigDecimal precoMaoObraRenda = BigDecimal.ZERO;
			if (custoMaoObraRenda.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraRenda.get()
						.getCusto());
				precoMaoObraRenda = custoMaoObraRenda.get().getValorVenda();

				tempoMedioPorUnidade += custoMaoObraRenda.get()
						.getTempoMedioPorUnidade();

			}
			custoUnidade = custoUnidade.add(renda.getPrecoCusto(modelo));
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);
			BigDecimal precoVenda = renda.getPrecoVenda(modelo)
					.add(precoMaoObraRenda).add(precoMaoObraCorteRenda);
			o.addItem(o.new Item(renda.toString(), precoVenda));
		}
	}

	public void calcularAplicacaoSerigrafiaInterno(Orcamento o) {
		if (serigrafiaInterno != null) {

			Optional<MaoObra> custoMaoObraRenda = getCustoMaoObra(TipoMaoObra.SEPARACAO_SERIGRAFIA);
			BigDecimal precoMaoObra = BigDecimal.ZERO;
			if (custoMaoObraRenda.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraRenda.get()
						.getCusto());
				precoMaoObra = custoMaoObraRenda.get().getValorVenda();
			}

			custoUnidade = custoUnidade.add(serigrafiaInterno.getCusto());
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);
			o.addItem(o.new Item(serigrafiaInterno.toString(),
					serigrafiaInterno.getPrecoVenda().add(precoMaoObra)));
		}
	}

	public void calcularAplicacaoSerigrafiaEnvelope(Orcamento o) {
		if (serigrafiaEnvelope != null) {
			Optional<MaoObra> custoMaoObraRenda = getCustoMaoObra(TipoMaoObra.SEPARACAO_SERIGRAFIA);
			BigDecimal precoMaoObra = BigDecimal.ZERO;
			if (custoMaoObraRenda.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObraRenda.get()
						.getCusto());
				precoMaoObra = custoMaoObraRenda.get().getValorVenda();
			}

			custoUnidade = custoUnidade.add(serigrafiaEnvelope.getCusto());
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);
			o.addItem(o.new Item(serigrafiaEnvelope.toString(),
					serigrafiaEnvelope.getPrecoVenda().add(precoMaoObra)));
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

			Optional<MaoObra> custoMaoObra = getCustoMaoObra(TipoMaoObra.COLOCACAO_PEROLA);
			BigDecimal precoMaoObra = BigDecimal.ZERO;
			if (custoMaoObra.isPresent()) {
				custoUnidade = custoUnidade.add(custoMaoObra.get().getCusto()
						.multiply(new BigDecimal(quantidadeStrass)));
				precoMaoObra = custoMaoObra.get().getValorVenda()
						.multiply(new BigDecimal(quantidadeStrass));

				tempoMedioPorUnidade += (custoMaoObra.get()
						.getTempoMedioPorUnidade() * quantidadeStrass);
			}

			custoUnidade = custoUnidade.add(strass
					.getPrecoCusto(quantidadeStrass));
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

			BigDecimal precoVenda = strass.getPrecoVenda(quantidadeStrass);
			precoVenda = precoVenda.add(precoMaoObra);
			o.addItem(o.new Item(strass.toString(), precoVenda));
		}
	}

	public void calcularFechamentoIma(Orcamento o) {
		if (ima != null) {
			Optional<MaoObra> custo = getCustoMaoObra(TipoMaoObra.COLOCACAO_IMA);
			BigDecimal precoVendaMaoObra = BigDecimal.ZERO;
			if (custo.isPresent()) {
				custoUnidade = custoUnidade.add(custo.get().getCusto());
				precoVendaMaoObra = custo.get().getValorVenda();
				tempoMedioPorUnidade += (custo.get().getTempoMedioPorUnidade());
			}

			custoUnidade = custoUnidade.add(ima.getCusto());
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

			BigDecimal precoVenda = ima.getPrecoVenda();
			precoVenda = precoVenda.add(precoVendaMaoObra);
			o.addItem(o.new Item(ima.toString(), precoVenda));
		}
	}

	public void calcularPrecoCorte(Orcamento o) {
		if (modelo.isCobrarCorte() && corte != null) {
			if (corte != null) {
				custoUnidade = custoUnidade.add(corte
						.getCusto(quantidadeConvites));
				atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

				o.addItem(o.new Item(corte.toString(), corte
						.getPrecoVenda(quantidadeConvites)));
			}
		}
	}

	public void calcularPrecoCorteInternoAlmofadado(Orcamento o) {
		if (corteInternoAlmofadado != null) {
			custoUnidade = custoUnidade.add(corteInternoAlmofadado
					.getCusto(quantidadeConvites));
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);
			o.addItem(o.new Item(corteInternoAlmofadado.toString(), corte
					.getPrecoVenda(quantidadeConvites)));
		}
	}

	public void calcularPrecoAcoplamentoEvelope(Orcamento o) {
		if (acoplamentoEnvelope != null) {

			BigDecimal valorEnvelopeCusto = this.modelo
					.getCustoAtual(papelEnvelope);
			BigDecimal valorEnvelopeVenda = this.modelo
					.getPrecoVenda(papelEnvelope);

			custoUnidade = custoUnidade.add(acoplamentoEnvelope.getCusto(
					quantidadeConvites).add(valorEnvelopeCusto));
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

			o.addItem(o.new Item(acoplamentoEnvelope.toString() + "Envelope",
					acoplamentoEnvelope.getPrecoVenda(quantidadeConvites).add(
							valorEnvelopeVenda)));

		}
	}

	public void calcularPrecoAcoplamentoInterno(Orcamento o) {
		if (acoplamentoInterno != null) {

			int quantidadeFolhasParaPapelInterno = modelo
					.getTamanhoItemInterno();
			if (papelInterno != null && quantidadeFolhasParaPapelInterno > 0) {

				BigDecimal valorCustoUnidade = this.papelInterno
						.getCustoAtual()
						.divide(new BigDecimal(quantidadeFolhasParaPapelInterno),
								4, RoundingMode.HALF_UP);

				BigDecimal valorPapelVenda = this.papelInterno.getPrecoAtual();
				valorPapelVenda = valorPapelVenda.divide(
						new BigDecimal(quantidadeFolhasParaPapelInterno), 4,
						RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);

				custoUnidade = custoUnidade.add(acoplamentoInterno.getCusto(
						quantidadeConvites).add(valorCustoUnidade));
				atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);

				o.addItem(o.new Item(
						acoplamentoInterno.toString() + " Interno",
						acoplamentoInterno.getPrecoVenda(quantidadeConvites)
								.add(valorPapelVenda)));

			} else {
				throw new IllegalArgumentException(
						"Para calcular acoplagem do papel interno, é necessário selecionar um papel interno!");
			}
		}
	}

	public void calcularEmbalagem(Orcamento o) {
		if (this.embalagem != null) {

			Optional<MaoObra> custoEtiqueta = getCustoMaoObra(TipoMaoObra.COLOCACAO_ETIQUETA);
			BigDecimal precoEtiqueta = BigDecimal.ZERO;
			if (custoEtiqueta.isPresent()) {
				custoUnidade = custoUnidade.add(custoEtiqueta.get().getCusto());
				precoEtiqueta = custoEtiqueta.get().getValorVenda();
				tempoMedioPorUnidade += (custoEtiqueta.get()
						.getTempoMedioPorUnidade());

			}

			Optional<MaoObra> custo = getCustoMaoObra(TipoMaoObra.COLOCACAO_SACO_PLASTICO);
			BigDecimal precoVendaMaoObra = BigDecimal.ZERO;
			if (custo.isPresent()) {
				custoUnidade = custoUnidade.add(custo.get().getCusto());
				precoVendaMaoObra = custo.get().getValorVenda();
				tempoMedioPorUnidade += (custo.get().getTempoMedioPorUnidade());
			}

			custoUnidade = custoUnidade.add(embalagem.getCustoAtual());
			atualizaCustoUnidade(o, custoUnidade,tempoMedioPorUnidade);
			BigDecimal precoVenda = embalagem.getPrecoVenda();
			precoVenda = precoVenda.add(precoVendaMaoObra).add(precoEtiqueta);
			o.addItem(o.new Item(embalagem.toString(), precoVenda));
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
		private int tempoEstimado;

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

		public int getTempoEstimado() {
			return tempoEstimado;
		}

		public void setTempoEstimado(int tempoEstimado) {
			this.tempoEstimado = tempoEstimado;
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
				BigDecimal valorParcela = valorTotal.divide(new BigDecimal(i),
						2, RoundingMode.HALF_UP);
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
		private Papel papelRevestimentoInterno;
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
		private CorteEnvelope corteInternoAlmofadado;
		private Cliche cliche;
		private BigDecimal taxaAdministracaoCartaoCredito;
		private BigDecimal taxaAdministracaoCartaoDebito;
		private BigDecimal taxaJurosMensal;
		private Acoplamento acoplamentoEnvelope;
		private Acoplamento acoplamentoInterno;
		private List<MaoObra> custoMaoObra;

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

		public CalculadoraBuilder papelRevestimentoInterno(Papel papel) {
			this.papelRevestimentoInterno = papel;
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

		public CalculadoraBuilder corteInternoAlmofadado(
				CorteEnvelope corteInternoAlmofadado) {
			this.corteInternoAlmofadado = corteInternoAlmofadado;
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

		public CalculadoraBuilder acoplamentoEnvelope(Acoplamento acoplamento) {
			this.acoplamentoEnvelope = acoplamento;
			return this;
		}

		public CalculadoraBuilder acoplamentoInterno(Acoplamento acoplamento) {
			this.acoplamentoInterno = acoplamento;
			return this;
		}

		public CalculadoraBuilder maoObra(List<MaoObra> custoMaoObra) {
			this.custoMaoObra = custoMaoObra;
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
					impressaoNome, corte, corteInternoAlmofadado, cliche,
					taxaAdministracaoCartaoCredito,
					taxaAdministracaoCartaoDebito, taxaJurosMensal,
					acoplamentoEnvelope, acoplamentoInterno,
					papelRevestimentoInterno, custoMaoObra);
		}
	}

}
