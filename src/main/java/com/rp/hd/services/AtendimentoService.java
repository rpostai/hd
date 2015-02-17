package com.rp.hd.services;

import java.io.FileInputStream;
import java.io.StringWriter;
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

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.google.common.io.ByteStreams;
import com.rp.hd.domain.ModeloConvite;
import com.rp.hd.domain.atendimento.Atendimento;
import com.rp.hd.repository.jpa.ColagemRepository;
import com.rp.hd.repository.jpa.ModeloConviteRepository;
import com.rp.hd.repository.jpa.OrcamentoRepository;
import com.rp.hd.repository.jpa.atendimento.AtendimentoRepository;

@Path("atendimento")
public class AtendimentoService {
	
	private static final SimpleDateFormat SD = new SimpleDateFormat("dd/MM/yyyy");

	@Inject
	private AtendimentoRepository repository;

	@Inject
	private OrcamentoRepository orcamentoRepository;

	@Inject
	private ModeloConviteRepository modeloConviteRepository;

	@Inject
	ColagemRepository colagemRepository;

	@Resource(name = "java:jboss/mail/Default")
	private Session session;

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
	@Path("atualizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Atendimento atualizar(Atendimento atendimento) {
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
			if (atendimento.getCliente1() != null && StringUtils.isNotBlank(atendimento.getCliente1().getEmail())) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						atendimento.getCliente1().getEmail(), atendimento
								.getCliente1().getNome()));
			}

			if (atendimento.getCliente2() != null && StringUtils.isNotBlank(atendimento.getCliente2().getEmail())) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						atendimento.getCliente2().getEmail(), atendimento
								.getCliente2().getNome()));
			}

			msg.setSubject(String.format("Orçamento %s",
					atendimento.getNumero()));
			
			List<SolicitacaoOrcamento> orcamentos = getOrcamentosAtendimento(atendimento
					.getId());

			Multipart mp = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(criarEmail(atendimento,orcamentos), "text/html");
			mp.addBodyPart(htmlPart);
			
			orcamentos.forEach(orc -> {
				if (orc.getModelo() != null) {
					Optional<ModeloConvite> ultimasFotosModelo = modeloConviteRepository.getUltimasFotosModelo(orc.getModelo().getId());	
					ultimasFotosModelo.ifPresent(modelo -> {
						modelo.getFotos().forEach(foto -> {
							FileInputStream file;
							try {
								file = new FileInputStream("/images/img1.jpg");
								byte[] bytes = ByteStreams.toByteArray(file);
								MimeBodyPart attachment = new MimeBodyPart();
								attachment.setFileName(modelo.getNome() +"_" +  RandomStringUtils.randomAlphabetic(4) + ".jpg");
								attachment.setContent(bytes, "image/jpeg");
								mp.addBodyPart(attachment);
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

			msg.setContent(mp);

			Transport.send(msg);
		} catch (Exception e) {
			throw new Exception(
					"O email não pode ser enviado com sucesso. Por favor imprima o orçamento",e);
		}
	}

	private String criarEmail(Atendimento atendimento, List<SolicitacaoOrcamento> orcamentos) {

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

		

		context.put("orcamentos", orcamentos);

		Template template = null;
		template = Velocity.getTemplate("orcamento.html","UTF-8");

		StringWriter sw = new StringWriter();

		template.merge(context, sw);

		return sw.toString();
	}

}
