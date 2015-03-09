package com.rp.hd.services;

import java.io.FileInputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.google.common.io.ByteStreams;
import com.rp.hd.domain.Complemento;
import com.rp.hd.domain.ModeloConvite;
import com.rp.hd.domain.OrigemContato;
import com.rp.hd.domain.atendimento.Atendimento;
import com.rp.hd.domain.atendimento.OrcamentoComplemento;
import com.rp.hd.domain.atendimento.PromocaoConvite;
import com.rp.hd.repository.jpa.ColagemRepository;
import com.rp.hd.repository.jpa.ComplementoRepository;
import com.rp.hd.repository.jpa.ModeloConviteRepository;
import com.rp.hd.repository.jpa.OrcamentoComplementoRepository;
import com.rp.hd.repository.jpa.OrcamentoRepository;
import com.rp.hd.repository.jpa.OrigemContatoRepository;
import com.rp.hd.repository.jpa.PromocaoConviteRepository;
import com.rp.hd.repository.jpa.atendimento.AtendimentoRepository;

@Path("atendimento")
public class AtendimentoService {
	
	private static final String caminhoFotos = "/imagem/";

	private static final SimpleDateFormat SD = new SimpleDateFormat(
			"dd/MM/yyyy");

	@Inject
	private AtendimentoRepository repository;

	@Inject
	private OrcamentoRepository orcamentoRepository;

	@Inject
	private ModeloConviteRepository modeloConviteRepository;

	@Inject
	private ComplementoRepository complementoRepository;

	@Inject
	private OrcamentoComplementoRepository orcamentoComplementoRepository;

	@Inject
	ColagemRepository colagemRepository;

	@Inject
	private PromocaoConviteRepository promocaoConviteRepository;

	@Resource(name = "java:jboss/mail/Default")
	private Session session;
	
	@Inject
	private OrigemContatoRepository origemContatoRepository;
	
	@GET
	@Path("origem")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrigemContato> getOrigemContato() {
		return origemContatoRepository.getTodos();
	}

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
	@Path("iniciar/{atendimentoOriginal}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Atendimento iniciarAtendimentoVinculado(
			@PathParam("atendimentoOriginal") Long atendimentoOriginalId) {
		Atendimento atendimentoOriginal = repository.get(atendimentoOriginalId);
		Atendimento atendimento = new Atendimento();
		atendimento.setAtendimentoPai(atendimentoOriginal);
		atendimento.iniciar();
		atendimento.setCliente1(atendimentoOriginal.getCliente1());
		atendimento.setCliente2(atendimentoOriginal.getCliente2());
		atendimento = repository.salvar(atendimento);
		return atendimento;
	}

	@POST
	@Path("atualizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Atendimento atualizar(Atendimento atendimento) {
		atendimento = repository.salvar(atendimento);
		return atendimento;
	}

	@POST
	@Path("fechar/{atendimento}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void fecharVenda(@PathParam("atendimento") Long atendimentoId) {
		Atendimento t = repository.get(atendimentoId);
		t.setDataFechamentoVenda(Calendar.getInstance().getTime());
		t.finalizar();
		repository.salvar(t);
	}

	@POST
	@Path("finalizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public long finalizarAtendimento(Atendimento atendimento) {
		atendimento.finalizar();
		atendimento = repository.salvar(atendimento);
		return atendimento.getTempoTotalAtendimento();
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
			ModeloFoto m = new ModeloFoto(modelo.get().getId(), modelo.get()
					.getNome(), null);
			modelo.get().getFotos().forEach(foto -> {
				m.addFoto(new Foto(foto.getOrdem(), foto.getCaminho()));
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
				.stream()
				.forEach(
						x -> {
							x.getFotos().forEach(
									foto -> {
										ModeloFoto m = new ModeloFoto(
												x.getId(), x.getNome(),
												new Foto(foto.getOrdem(), foto
														.getCaminho()));
										result.add(m);
									});
						});
		return result;
	}

	@GET
	@Path("orcamentos/{atendimento}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SolicitacaoOrcamento> getOrcamentosAtendimento(
			@PathParam("atendimento") Long atendimento) {
		return orcamentoRepository.getOrcamentosPorAtendimento(atendimento);
	}

	@POST
	@Path("complementossalvar/{atendimento}/{item}/{quantidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public BigDecimal incluirComplemento(
			@PathParam("atendimento") Long atendimento,
			@PathParam("item") Long item,
			@PathParam("quantidade") Integer quantidade) {

		List<OrcamentoComplemento> list = orcamentoComplementoRepository
				.getComplementos(atendimento);

		Optional<OrcamentoComplemento> orc = list.stream().filter(x -> {
			return x.getComplemento().getId().equals(item);
		}).findFirst();
		if (orc.isPresent()) {
			orc.get().setQuantidade(quantidade);
			orcamentoComplementoRepository.salvar(orc.get());
		} else {
			Atendimento at = repository.get(atendimento);
			Complemento c = complementoRepository.get(item);
			OrcamentoComplemento o = new OrcamentoComplemento();
			o.setAtendimento(at);
			o.setComplemento(c);
			o.setQuantidade(quantidade);
			o.setValor(c.getValorVenda());
			orcamentoComplementoRepository.salvar(o);
		}

		list = orcamentoComplementoRepository.getComplementos(atendimento);
		return list.stream().map(x -> {
			return x.getValor().multiply(new BigDecimal(x.getQuantidade()));
		}).reduce((x, y) -> {
			return x.add(y);
		}).get();
	}

	@POST
	@Path("consulta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Atendimento> consultaAtendimentos(ConsultaAtendimento consulta) {
		Calendar c = Calendar.getInstance();
		if (consulta.getData() != null && StringUtils.isNotBlank(consulta.getData())) {
			try {
				c.setTime(SD.parse(consulta.getData()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			c = null;
		}
		return repository
				.consultar(consulta.getNumero(), consulta.getNome(), c);
	}

	@GET
	@Path("complementos/{atendimento}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Dado> getComplementos(
			@PathParam("atendimento") Long atendimentoId) throws Exception {
		return complementoRepository.getComplementoAtivos();
	}

	@GET
	@Path("complementosatendimento/{atendimento}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrcamentoComplemento> getComplementosAtendimento(
			@PathParam("atendimento") Long atendimentoId) throws Exception {
		return orcamentoComplementoRepository.getComplementos(atendimentoId);
	}

	@GET
	@Path("promocoes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SolicitacaoOrcamento> getPromocoesAtivas() {
		List<PromocaoConvite> promocoes = promocaoConviteRepository
				.getPromocoesAtivas();

		return promocoes.stream().map(orcamento -> {
			
			SolicitacaoOrcamento s = new SolicitacaoOrcamento();
			s.setQuantidadeStrass(orcamento.getQuantidadeStrass());
			
			s.setModelo(new Dado(orcamento.getModelo().getId(), orcamento.getModelo().getNome()));
			s.setPapelEnvelope(new Dado(orcamento.getPapelEnvelope().getId(), orcamento.getPapelEnvelope().getNome()));
			
			if (orcamento.getPapelInterno() != null) {
				s.setPapelInterno(new Dado(orcamento.getPapelInterno().getId(), orcamento.getPapelInterno().getNome()));	
			}
			
			if (orcamento.getImpressaoEnvelope() != null) {
				s.setImpressaoEnvelope(new Dado(orcamento.getImpressaoEnvelope().getId(), orcamento.getImpressaoEnvelope().getDescricao()));
			}
			
			if (orcamento.getImpressaoInterno() != null) {
				s.setImpressaoInterno(new Dado(orcamento.getImpressaoInterno().getId(), orcamento.getImpressaoInterno().getDescricao()));
			}
			
			if (orcamento.getFita() != null) {
				s.setFita(new Dado(orcamento.getFita().getId(), orcamento.getFita().toString()));
			}
			
			if (orcamento.getLaco() != null) {
				s.setLaco(new Dado(orcamento.getLaco().getId(), orcamento.getLaco().getDescricao()));
			}
			
			if (orcamento.getRenda() != null) {
				s.setRenda(new Dado(orcamento.getRenda().getId(), orcamento.getRenda().getDescricao()));
			}
			
			if (orcamento.getImpressaoNome() != null) {
				s.setImpressaoNome(new Dado(orcamento.getImpressaoNome().getId(), orcamento.getImpressaoNome().getDescricao()));
			}
			
			if (orcamento.getHotstamp() != null) {
				s.setHotstamp(new Dado(orcamento.getHotstamp().getId(), orcamento.getHotstamp().getDescricao()));
				s.getHotstamp().setValor(orcamento.getHotstamp().getPrecoVenda());
			}
			
			if (orcamento.getIma() != null) {
				s.setIma(new Dado(orcamento.getIma().getId(), Integer.toString(orcamento.getIma().getTamanho())));
			}
			
			if (orcamento.getStrass() != null) {
				s.setStrass(new Dado(orcamento.getStrass().getId(), orcamento.getStrass().getDescricao()));
			}
			
			if (orcamento.getSerigrafiaEnvelope() != null) {
				s.setSerigrafiaEnvelope(new Dado(orcamento.getSerigrafiaEnvelope().getId(), orcamento.getSerigrafiaEnvelope().getDescricao()));
			}
			
			if (orcamento.getSerigrafiaInterno() != null) {
				s.setSerigrafiaInterno(new Dado(orcamento.getSerigrafiaInterno().getId(), orcamento.getSerigrafiaInterno().getDescricao()));
			}
			
			if (orcamento.getCliche() != null) {
				s.setCliche(new Dado(orcamento.getCliche().getId(), orcamento.getCliche().getDescricao()));
				s.getCliche().setValor(orcamento.getCliche().getValorVenda());
			}
			
			s.setPrecoCalculado(orcamento.getValor());
			
			if (CollectionUtils.isNotEmpty(orcamento.getFotos())) {
				orcamento.getFotos().stream().sorted((a,b) -> {
					return a.getOrdem() - b.getOrdem();
				}).forEach(foto -> {
					s.addFoto(caminhoFotos+ foto.getCaminho());
				});
			}
			
			return s;
		}).collect(Collectors.toList());
	}

	@GET
	@Path("enviaremail/{atendimento}")
	@Produces(MediaType.APPLICATION_JSON)
	public void enviarEmail(@PathParam("atendimento") Long atendimentoId)
			throws Exception {

		Atendimento atendimento = repository.get(atendimentoId);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"fernanda@happydayconviteria.com.br",
					"Happy Day Conviteria"));
			if (atendimento.getCliente1() != null
					&& StringUtils.isNotBlank(atendimento.getCliente1()
							.getEmail())) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						atendimento.getCliente1().getEmail(), atendimento
								.getCliente1().getNome()));
			}

			if (atendimento.getCliente2() != null
					&& StringUtils.isNotBlank(atendimento.getCliente2()
							.getEmail())) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						atendimento.getCliente2().getEmail(), atendimento
								.getCliente2().getNome()));
			}

			msg.setSubject(String.format("Orçamento %s",
					atendimento.getNumero()));

			List<SolicitacaoOrcamento> orcamentos = getOrcamentosAtendimento(atendimento
					.getId());

			Multipart mp = new MimeMultipart();

			boolean[] temImagens = new boolean[] { false };

			orcamentos
					.forEach(orc -> {
						if (orc.getModelo() != null) {
							Optional<ModeloConvite> ultimasFotosModelo = modeloConviteRepository
									.getUltimasFotosModelo(orc.getModelo()
											.getId());
							ultimasFotosModelo
									.ifPresent(modelo -> {
										modelo.getFotos()
												.forEach(
														foto -> {
															FileInputStream file;
															try {
																file = new FileInputStream(
																		"/images/img1.jpg");
																byte[] bytes = ByteStreams
																		.toByteArray(file);
																MimeBodyPart attachment = new MimeBodyPart();
																attachment
																		.setFileName(modelo
																				.getNome()
																				+ "_"
																				+ RandomStringUtils
																						.randomAlphabetic(4)
																				+ ".jpg");
																attachment
																		.setContent(
																				bytes,
																				"image/jpeg");
																mp.addBodyPart(attachment);

																temImagens[0] = true;
															} catch (Exception e) {
																e.printStackTrace();
															}

														});
									});
						}
					});

			// MimeBodyPart attachment = new MimeBodyPart();
			// attachment.setFileName("manual.pdf");
			// attachment.setContent(attachmentData, "application/pdf");
			// mp.addBodyPart(attachment);

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(
					criarEmail(atendimento, orcamentos, temImagens[0]),
					"text/html");
			mp.addBodyPart(htmlPart);

			msg.setContent(mp);

			Transport.send(msg);
		} catch (Exception e) {
			throw new Exception(
					"O email não pode ser enviado com sucesso. Por favor imprima o orçamento",
					e);
		}
	}

	private String criarEmail(Atendimento atendimento,
			List<SolicitacaoOrcamento> orcamentos, boolean temImagens) {

		Properties p = new Properties();
		p.setProperty("resource.loader", "file");
		p.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		p.setProperty("file.resource.loader.path", "/templates/");
		p.setProperty("file.resource.loader.cache", "false");
		p.setProperty("file.resource.loader.modificationCheckInterval", "0");

		Velocity.init(p);
		VelocityContext context = new VelocityContext();
		context.put("cliente1", atendimento.getCliente1().getNome());
		context.put("cliente2", atendimento.getCliente2().getNome());

		Calendar c = Calendar.getInstance();
		c.setTime(atendimento.getDataInicio().getTime());
		c.add(Calendar.DAY_OF_MONTH, 15);
		context.put("validade", SD.format(c.getTime()));

		if (CollectionUtils.isNotEmpty(orcamentos)) {
			context.put("orcamentos", orcamentos);
		}
		context.put("temImagens", temImagens);
		List<OrcamentoComplemento> complementos = orcamentoComplementoRepository
				.getComplementos(atendimento.getId());
		if (CollectionUtils.isNotEmpty(complementos)) {
			context.put("complementos", complementos);
		}

		Template template = null;
		template = Velocity.getTemplate("orcamento.html", "UTF-8");

		StringWriter sw = new StringWriter();

		template.merge(context, sw);

		return sw.toString();
	}

}
