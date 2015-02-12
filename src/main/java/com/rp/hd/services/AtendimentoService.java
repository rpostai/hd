package com.rp.hd.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rp.hd.domain.ModeloConvite;
import com.rp.hd.domain.atendimento.Atendimento;
import com.rp.hd.repository.jpa.ColagemRepository;
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

@Path("atendimento")
public class AtendimentoService {

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
	ColagemRepository colagemRepository;

	@POST
	@Path("iniciar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Atendimento iniciarAtendimento() {
		Atendimento atendimento = new Atendimento();
		atendimento.iniciar();
		atendimento = repository.salvar(atendimento);
		return atendimento;
	}

	@POST
	@Path("finalizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public long finalizarAtendimento(Atendimento atendimento) {
		atendimento.finalizar();
		atendimento = repository.salvar(atendimento);
		return atendimento.calcularTempoTotalAtendimento();
	}

	@GET
	@Path("modelos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModeloFoto> getFotosModelos() {
		return modeloConviteRepository
				.getModelosComFotos()
				.stream()
				.map(modelo -> {
					if (modelo.getFotos().size() > 0) {
						String caminho = modelo.getFotos().stream()
								.sorted((foto1, foto2) -> {
									return foto1.getOrdem() - foto2.getOrdem();
								}).collect(Collectors.toList()).get(0)
								.getCaminho();

						ModeloFoto m = new ModeloFoto(modelo.getId(), modelo
								.getNome(), caminho);
						return m;
					} else {
						ModeloFoto m = new ModeloFoto(modelo.getId(), modelo
								.getNome(), null);
						return m;
					}
				}).collect(Collectors.toList());
	}

	@GET
	@Path("modelos/{modelo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ModeloFoto getFotosModelo(@PathParam("modelo") Long modeloId) {
		Optional<ModeloConvite> modelo = modeloConviteRepository
				.getModeloComFotos(modeloId);
		if (modelo.isPresent()) {
			ModeloFoto m = new ModeloFoto(modelo.get().getId(), modelo.get().getNome(), null);
			modelo.get().getFotos().forEach(foto -> {
				m.addFoto(foto.getCaminho());
			});
			return m;
		}
		return null;
	}

	// @POST
	// @Path("orcamento/{atendimento}")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public void adicionarOrcmaneto(@PathParam("atendimento") Long
	// atendimentoId, SolicitacaoOrcamento sol) {
	// Atendimento atendimento = repository.get(atendimentoId);
	// if (atendimento != null) {
	// throw new
	// IllegalArgumentException("Para realizar um orçamento é obrigatório ter um atendimento iniciado");
	// }
	//
	// Orcamento o = new Orcamento();
	// o.setQuantidade(sol.getQuantidade());
	//
	// if (sol.getModelo() != null) {
	// o.setModelo(modeloConviteRepository.get(sol.getModelo().getId()));
	// }
	//
	// if (sol.getPapelEnvelope() != null) {
	// o.setPapelEnvelope(papelRepository.get(sol.getPapelEnvelope().getId()));
	// }
	//
	// if (sol.getPapelInterno() != null) {
	// o.setPapelInterno(papelRepository.get(sol.getPapelInterno().getId()));
	// }
	//
	// if (sol.getFita() != null) {
	// o.setFita(fitaRepository.get(sol.getFita().getId()));
	// }
	//
	// if (sol.getLaco() != null) {
	// o.setLaco(lacoRepository.get(sol.getLaco().getId()));
	// }
	//
	// if (sol.getHotstamp() != null) {
	// o.setHotstamp(hotStampRepository.get(sol.getHotstamp().getId()));
	// }
	//
	// if (sol.getIma() != null) {
	// o.setIma(imaRepository.get(sol.getIma().getId()));
	// }
	//
	// if (sol.getRenda() != null) {
	// o.setRenda(rendaRepository.get(sol.getRenda().getId()));
	// }
	//
	// if (sol.getImpressaoEnvelope() != null) {
	// o.setImpressaoEnvelope(impressaoRepository.get(sol.getImpressaoEnvelope().getId()));
	// }
	//
	// if (sol.getImpressaoInterno() != null) {
	// o.setImpressaoInterno(impressaoRepository.get(sol.getImpressaoInterno().getId()));
	// }
	//
	// if (sol.getImpressaoNome() != null) {
	// o.setImpressaoNome(impressaoNomeRepository.get(sol.getImpressaoNome().getId()));
	// }
	//
	// if (sol.getSerigrafiaEnvelope() != null) {
	// o.setSerigrafiaEnvelope(serigrafiaRepository.get(sol.getSerigrafiaEnvelope().getId()));
	// }
	//
	// if (sol.getSerigrafiaInterno() != null) {
	// o.setSerigrafiaInterno(serigrafiaRepository.get(sol.getSerigrafiaInterno().getId()));
	// }
	//
	// if (sol.getQuantidadeStrass() > 0) {
	// o.setQuantidadeStrass(sol.getQuantidadeStrass());
	// }
	//
	// if (sol.getStrass() != null) {
	// o.setStrass(strassRepository.get(sol.getStrass().getId()));
	// }
	//
	// orcamentoRepository.salvar(o);
	//
	// }

}
