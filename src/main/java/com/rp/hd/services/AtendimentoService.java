package com.rp.hd.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rp.hd.domain.atendimento.Atendimento;
import com.rp.hd.repository.jpa.atendimento.AtendimentoRepository;

@Path("atendimento")
public class AtendimentoService {
	
	@Inject
	private AtendimentoRepository repository;

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
	
	@POST
	@Path("pessoa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Atendimento marcarPessoa(Atendimento atendimento) {
		repository.salvar(atendimento);
		return atendimento;
	}
	
	
	
	
}
