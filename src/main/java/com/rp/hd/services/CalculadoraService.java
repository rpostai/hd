package com.rp.hd.services;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rp.hd.domain.Calculadora;
import com.rp.hd.domain.Calculadora.Orcamento;
import com.rp.hd.domain.Cliche;
import com.rp.hd.domain.Colagem;
import com.rp.hd.domain.Configuracao;
import com.rp.hd.domain.Constants;
import com.rp.hd.domain.CorteEnvelope;
import com.rp.hd.domain.Fita;
import com.rp.hd.domain.HotStamp;
import com.rp.hd.domain.Ima;
import com.rp.hd.domain.Impressao;
import com.rp.hd.domain.ImpressaoNome;
import com.rp.hd.domain.Laco;
import com.rp.hd.domain.ModeloConvite;
import com.rp.hd.domain.Papel;
import com.rp.hd.domain.Renda;
import com.rp.hd.domain.Serigrafia;
import com.rp.hd.domain.Strass;
import com.rp.hd.domain.atendimento.Atendimento;
import com.rp.hd.repository.jpa.ClicheRepository;
import com.rp.hd.repository.jpa.ColagemRepository;
import com.rp.hd.repository.jpa.ConfiguracaoRepository;
import com.rp.hd.repository.jpa.CorteEnvelopeRepository;
import com.rp.hd.repository.jpa.FitaRepository;
import com.rp.hd.repository.jpa.HotStampRepository;
import com.rp.hd.repository.jpa.ImaRepository;
import com.rp.hd.repository.jpa.ImpressaoNomeRepository;
import com.rp.hd.repository.jpa.ImpressaoRepository;
import com.rp.hd.repository.jpa.LacoRepository;
import com.rp.hd.repository.jpa.ModeloConviteRepository;
import com.rp.hd.repository.jpa.OrcamentoRepository;
import com.rp.hd.repository.jpa.PapelRepository;
import com.rp.hd.repository.jpa.RendaRepository;
import com.rp.hd.repository.jpa.SerigrafiaRepository;
import com.rp.hd.repository.jpa.StrassRepository;
import com.rp.hd.repository.jpa.atendimento.AtendimentoRepository;

@Stateless
@Path("calculadora")
public class CalculadoraService {
	@Inject
	private AtendimentoRepository repository;
	
	@Inject
	private OrcamentoRepository orcamentoRepository;

	@Inject
	private PapelRepository papelRepository;

	@Inject
	private ModeloConviteRepository modeloConviteRepository;

	@Inject
	private ImpressaoRepository impressaoRepository;

	@Inject
	private LacoRepository lacoRepository;

	@Inject
	private ImaRepository imaRepository;

	@Inject
	private HotStampRepository hotStampRepository;

	@Inject
	private FitaRepository fitaRepository;

	@Inject
	private StrassRepository strassRepository;

	@Inject
	private SerigrafiaRepository serigrafiaRepository;

	@Inject
	private RendaRepository rendaRepository;

	@Inject
	private ImpressaoNomeRepository impressaoNomeRepository;

	@Inject
	private CorteEnvelopeRepository corteEnvelopeRepository;

	@Inject
	private ColagemRepository colagemRepository;
	
	@Inject
	private ClicheRepository clicheRepository;
	
	@Inject
	private ConfiguracaoRepository configuracaoRepository;
	

	@GET
	@Path("atendimento")
	@Produces(MediaType.APPLICATION_JSON)
	public CalculadoraDados getDadosCalculadora() {
		CalculadoraDados dados = new CalculadoraDados();

		papelRepository.getTodos().forEach(papel -> {
			dados.addPapel(papel);
		});

		modeloConviteRepository.getTodos().forEach(modelo -> {
			dados.addModelo(modelo);
		});

		impressaoRepository.getTodos().forEach(impressao -> {
			dados.addImpressao(impressao);
		});

		lacoRepository.getTodos().forEach(laco -> {
			dados.addLaco(laco);
		});

		imaRepository.getTodos().forEach(ima -> {
			dados.addIma(ima);
		});

		hotStampRepository.getTodos().forEach(hot -> {
			dados.addHotStamp(hot);
		});

		fitaRepository.getTodos().forEach(fita -> {
			dados.addFita(fita);
		});

		strassRepository.getTodos().forEach(strass -> {
			dados.addStrass(strass);
		});

		serigrafiaRepository.getTodos().forEach(serigrafia -> {
			dados.addSerigrafia(serigrafia);
		});

		rendaRepository.getTodos().forEach(renda -> {
			dados.addRenda(renda);
		});

		impressaoNomeRepository.getTodos().forEach(imp -> {
			dados.addImpressaoNome(imp);
		});
		
		clicheRepository.getTodos().forEach(c -> {
			dados.addCliche(c);
		});

		return dados;
	}

	@POST
	@Path("calcular/{atendimento}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Orcamento calcular(@PathParam("atendimento") Long atendimentoId, SolicitacaoOrcamento orcamento) {

		if (orcamento.getQuantidade() == 0) {
			throw new IllegalArgumentException(
					"Quantidade deve ser maior do que zero");
		}

		if (orcamento.getModelo() == null) {
			throw new IllegalArgumentException(
					"Modelo de convite ï¿½ obrigatï¿½rio");
		}

		ModeloConvite m = modeloConviteRepository.get(orcamento.getModelo().getId());

		Papel papelEnvelopeAplicado = null;
		if (orcamento.getPapelEnvelope() != null) {
			papelEnvelopeAplicado = papelRepository.get(orcamento.getPapelEnvelope().getId());
		}
		Papel papelInternoAplicado = null;
		if (orcamento.getPapelInterno() != null) {
			papelInternoAplicado = papelRepository.get(orcamento.getPapelInterno().getId());
		}
		Impressao impressaoEnvelopeAplicado = null;
		if (orcamento.getImpressaoEnvelope() != null) {
			impressaoEnvelopeAplicado = impressaoRepository
					.get(orcamento.getImpressaoEnvelope().getId());
		}
		Impressao impressaoInternoAplicado = null;
		if (orcamento.getImpressaoInterno() != null) {
			impressaoInternoAplicado = impressaoRepository
					.get(orcamento.getImpressaoInterno().getId());
		}
		Renda rendaAplicada = null;
		if (orcamento.getRenda() != null) {
			rendaAplicada = rendaRepository.get(orcamento.getRenda().getId());
		}
		Ima imaAplicado = null;
		if (orcamento.getIma() != null) {
			imaAplicado = imaRepository.get(orcamento.getIma().getId());
		}
		HotStamp hotStampAplicado = null;
		if (orcamento.getHotstamp() != null) {
			hotStampAplicado = hotStampRepository.get(orcamento.getHotstamp().getId());
		}
		Strass strassAplicado = null;
		if (orcamento.getStrass() != null) {
			strassAplicado = strassRepository.get(orcamento.getStrass().getId());
		}
		ImpressaoNome impressaoNomeAplicado = null;
		if (orcamento.getImpressaoNome() != null) {
			impressaoNomeAplicado = impressaoNomeRepository.get(orcamento.getImpressaoNome().getId());
		}
		Fita fitaAplicada = null;
		if (orcamento.getFita() != null) {
			fitaAplicada = fitaRepository.get(orcamento.getFita().getId());
		}

		Laco lacoAplicado = null;
		if (orcamento.getLaco() != null) {
			lacoAplicado = lacoRepository.get(orcamento.getLaco().getId());
		}

		Serigrafia serigrafiaAplicadaEnvelope = null;
		if (orcamento.getSerigrafiaEnvelope() != null ) {
			serigrafiaAplicadaEnvelope = serigrafiaRepository
					.get(orcamento.getSerigrafiaEnvelope().getId());
		}

		Serigrafia serigrafiaAplicadaInterno = null;
		if (orcamento.getSerigrafiaInterno() != null) {
			serigrafiaAplicadaInterno = serigrafiaRepository
					.get(orcamento.getSerigrafiaInterno().getId());
		}

		CorteEnvelope corte = corteEnvelopeRepository.getTodos().get(0);
		Colagem colagem = colagemRepository.getTodos().get(0);
		
		Cliche cliche = null;
		if (orcamento.getCliche() != null) {
			cliche = clicheRepository.get(orcamento.getCliche().getId());
		}
		
		Optional<Configuracao> taxaJurosMensal = configuracaoRepository.getConfiguracao(Constants.TAXA_JUROS_MENSAL.toString());
		Optional<Configuracao> taxaAdministracaoCartaoCredito = configuracaoRepository.getConfiguracao(Constants.TAXA_ADMINISTRACAO_CARTAO_CREDITO.toString());
		Optional<Configuracao> taxaAdministraaoCartaoDebito = configuracaoRepository.getConfiguracao(Constants.TAXA_ADMINISTRACAO_CARTAO_DEBITO.toString());

		Calculadora.CalculadoraBuilder builder = Calculadora.CalculadoraBuilder
				.getInstance();

		builder.quantidadeConvites(orcamento.getQuantidade())
				.modeloConvite(m).papelEnvelope(papelEnvelopeAplicado)
				.papelInterno(papelInternoAplicado).colagem(colagem)
				.corte(corte).impressaoEnvelope(impressaoEnvelopeAplicado)
				.impressaoInterno(impressaoInternoAplicado).fita(fitaAplicada)
				.laco(lacoAplicado).hotstamp(hotStampAplicado)
				.strass(strassAplicado, orcamento.getQuantidadeStrass()).renda(rendaAplicada)
				.ima(imaAplicado).impressaoNome(impressaoNomeAplicado)
				.serigrafiaEnvelope(serigrafiaAplicadaEnvelope)
				.serigrafiaInterno(serigrafiaAplicadaInterno).cliche(cliche);
		
		if (taxaAdministraaoCartaoDebito.isPresent()) {
			builder.taxaAdministracaoCartaoDebito(new BigDecimal(taxaAdministraaoCartaoDebito.get().getValor()));
		}
		
		if (taxaAdministracaoCartaoCredito.isPresent()) {
			builder.taxaAdministracaoCartaoCredito(new BigDecimal(taxaAdministracaoCartaoCredito.get().getValor()));
		}
		
		if (taxaJurosMensal.isPresent()) {
			builder.taxaJurosMensal(new BigDecimal(taxaJurosMensal.get().getValor()));
		}
		
		Calculadora calc = builder.build();
		
		Orcamento resultado =  calc.calcular();
		
		adicionarOrcamento(atendimentoId, orcamento, resultado);
		
		resultado.setCustoOutros(null);
		resultado.setCustoUnidade(null);
		
		return resultado;
	}
	
	public void adicionarOrcamento(@PathParam("atendimento") Long atendimentoId, SolicitacaoOrcamento sol, Orcamento orcamentoCalculado) {
		
		Atendimento atendimento = repository.get(atendimentoId);
		if (atendimento == null) {
			throw new IllegalArgumentException("Para realizar um orçamento é obrigatório ter um atendimento iniciado");
		}
		
		com.rp.hd.domain.atendimento.Orcamento o = new com.rp.hd.domain.atendimento.Orcamento();
		
		o.setAtendimento(atendimento);
		
		o.setPrecoCalculado(orcamentoCalculado.getValorUnidade());
		o.setPrecoCalculadoPrazo(orcamentoCalculado.getValorUnidadePrazo());
		
		o.setPrecoCalculadoItemsPedido(orcamentoCalculado.getValorItemsPorPedido());
		o.setPrecoCalculadoItemsPedidoPrazo(orcamentoCalculado.getValorItemsPorPedidoPrazo());
		
		o.setPrecoCalculadoTotal(orcamentoCalculado.getValorTotal());
		o.setPrecoCalculadoTotalPrazo(orcamentoCalculado.getValorTotalPrazo());
		
		o.setPrecoCalculadoConvites(orcamentoCalculado.getValorTotalConvites());
		o.setPrecoCalculadoConvitesPrazo(orcamentoCalculado.getValorTotalConvitesPrazo());
		
		o.setQuantidade(sol.getQuantidade());
		
		o.setCustoUnidade(orcamentoCalculado.getCustoUnidade());
		
		o.setCustoOutros(orcamentoCalculado.getCustoOutros());
		
		
		if (sol.getModelo() != null) {
			o.setModelo(modeloConviteRepository.get(sol.getModelo().getId()));	
		}
		
		if (sol.getPapelEnvelope() != null) {
			o.setPapelEnvelope(papelRepository.get(sol.getPapelEnvelope().getId()));
		}
		
		if (sol.getPapelInterno() != null) {
			o.setPapelInterno(papelRepository.get(sol.getPapelInterno().getId()));
		}
		
		if (sol.getFita() != null) {
			o.setFita(fitaRepository.get(sol.getFita().getId()));
		}
		
		if (sol.getLaco() != null) {
			o.setLaco(lacoRepository.get(sol.getLaco().getId()));
		}
		
		if (sol.getHotstamp() != null) {
			o.setHotstamp(hotStampRepository.get(sol.getHotstamp().getId()));
		}
		
		if (sol.getIma() != null) {
			o.setIma(imaRepository.get(sol.getIma().getId()));
		}
		
		if (sol.getRenda() != null) {
			o.setRenda(rendaRepository.get(sol.getRenda().getId()));
		}
		
		if (sol.getImpressaoEnvelope() != null) {
			o.setImpressaoEnvelope(impressaoRepository.get(sol.getImpressaoEnvelope().getId()));
		}
		
		if (sol.getImpressaoInterno() != null) {
			o.setImpressaoInterno(impressaoRepository.get(sol.getImpressaoInterno().getId()));
		}
		
		if (sol.getImpressaoNome() != null) {
			o.setImpressaoNome(impressaoNomeRepository.get(sol.getImpressaoNome().getId()));
		}
		
		if (sol.getSerigrafiaEnvelope() != null) {
			o.setSerigrafiaEnvelope(serigrafiaRepository.get(sol.getSerigrafiaEnvelope().getId()));
		}
		
		if (sol.getSerigrafiaInterno() != null) {
			o.setSerigrafiaInterno(serigrafiaRepository.get(sol.getSerigrafiaInterno().getId()));
		}
		
		if (sol.getQuantidadeStrass() > 0) {
			o.setQuantidadeStrass(sol.getQuantidadeStrass());
		}
		
		if (sol.getStrass() != null) {
			o.setStrass(strassRepository.get(sol.getStrass().getId()));
		}
		
		if (sol.getCliche() != null) {
			o.setCliche(clicheRepository.get(sol.getCliche().getId()));
		}
		
		orcamentoRepository.salvar(o);
		
	}
	
}
