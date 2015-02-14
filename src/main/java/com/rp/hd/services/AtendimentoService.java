package com.rp.hd.services;

import java.util.ArrayList;
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
import com.rp.hd.domain.atendimento.Orcamento;
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
								.getNome(), new Foto(1, caminho));
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
				m.addFoto(new Foto (foto.getOrdem(), foto.getCaminho()));
			});
			return m;
		}
		return null;
	}
	
	@GET
	@Path("fotos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModeloFoto> getTodasFotos() {
		List<ModeloFoto> result = new ArrayList<ModeloFoto>();
		modeloConviteRepository
				.getModelosComFotos()
				.stream().forEach(x -> {
					x.getFotos().forEach(foto-> {
						ModeloFoto m = new ModeloFoto(x.getId(),x.getNome(), new Foto(foto.getOrdem(), foto.getCaminho()));
						result.add(m);
					});
				});
		return result;
	}
	
	@GET
	@Path("orcamentos/{atendimento}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SolicitacaoOrcamento> getOrcamentosAtendimento(@PathParam("atendimento") Long atendimento) {
		return orcamentoRepository.getOrcamentosPorAtendimento(atendimento);
	}
}
