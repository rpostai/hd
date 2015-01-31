package com.rp.hd.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rp.hd.domain.Calculadora;
import com.rp.hd.domain.Calculadora.Orcamento;
import com.rp.hd.domain.Colagem;
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
import com.rp.hd.repository.jpa.ColagemRepository;
import com.rp.hd.repository.jpa.CorteEnvelopeRepository;
import com.rp.hd.repository.jpa.FitaRepository;
import com.rp.hd.repository.jpa.HotStampRepository;
import com.rp.hd.repository.jpa.ImaRepository;
import com.rp.hd.repository.jpa.ImpressaoNomeRepository;
import com.rp.hd.repository.jpa.ImpressaoRepository;
import com.rp.hd.repository.jpa.LacoRepository;
import com.rp.hd.repository.jpa.ModeloConviteRepository;
import com.rp.hd.repository.jpa.PapelRepository;
import com.rp.hd.repository.jpa.RendaRepository;
import com.rp.hd.repository.jpa.SerigrafiaRepository;
import com.rp.hd.repository.jpa.StrassRepository;

@Stateless
public class CalculadoraService {

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

		return dados;
	}

	public Orcamento calcular(int quantidade, Long modelo,
			Long papelEnvelope, Long papelinterno, Long impressaoEnvelope, Long impressaoInterno,
			Long fita, Long laco,
			Long impressaoNome, Long renda, Long serigrafiaEnvelope,
			Long serigrafiaInterno, Long hotstamp, int quantidadeStrass,
			Long strass, Long ima) {

		if (quantidade == 0) {
			throw new IllegalArgumentException(
					"Quantidade deve ser maior do que zero");
		}

		if (modelo == null) {
			throw new IllegalArgumentException(
					"Modelo de convite é obrigatório");
		}

		ModeloConvite m = modeloConviteRepository.get(modelo);
		
		Papel papelEnvelopeAplicado = null;
		if (papelEnvelope != null && papelEnvelope > 0) {
			papelEnvelopeAplicado = papelRepository.get(papelEnvelope);
		}
		Papel papelInternoAplicado = null;
		if (papelinterno != null && papelinterno > 0) {
			papelInternoAplicado = papelRepository.get(papelinterno);
		}
		Impressao impressaoEnvelopeAplicado = null;
		if (impressaoEnvelope != null && impressaoEnvelope > 0) {
			impressaoEnvelopeAplicado = impressaoRepository.get(impressaoEnvelope);
		}
		Impressao impressaoInternoAplicado = null;
		if (impressaoInterno != null && impressaoInterno > 0) {
			impressaoInternoAplicado = impressaoRepository.get(impressaoInterno);
		}
		Renda rendaAplicada = null;
		if (renda != null && renda !=0) {
			rendaAplicada = rendaRepository.get(renda);
		}
		Ima imaAplicado = null;
		if (ima != null && ima > 0) {
			imaAplicado = imaRepository.get(ima);
		}
		HotStamp hotStampAplicado = null;
		if (hotstamp != null && hotstamp > 0) {
			hotStampAplicado = hotStampRepository.get(hotstamp);
		}
		Strass strassAplicado = null;
		if (strass != null && strass >0 ) {
			strassAplicado = strassRepository.get(strass);
		}
		ImpressaoNome impressaoNomeAplicado = null;
		if (impressaoNome != null && impressaoNome > 0) {
			impressaoNomeAplicado = impressaoNomeRepository.get(impressaoNome);
		}
		Fita fitaAplicada = null;
		if (fita != null && fita >0) {
			fitaAplicada = fitaRepository.get(fita);
		}
		
		Laco lacoAplicado = null;
		if (laco != null && laco > 0) {
			lacoAplicado = lacoRepository.get(laco);
		}
		
		Serigrafia serigrafiaAplicadaEnvelope = null;
		if (serigrafiaEnvelope != null && serigrafiaEnvelope > 0) {
			serigrafiaAplicadaEnvelope = serigrafiaRepository.get(serigrafiaEnvelope);
		}
		
		Serigrafia serigrafiaAplicadaInterno = null;
		if (serigrafiaInterno != null && serigrafiaInterno > 0) {
			serigrafiaAplicadaInterno = serigrafiaRepository.get(serigrafiaInterno);
		}

		CorteEnvelope corte = corteEnvelopeRepository.getTodos().get(0);
		Colagem colagem = colagemRepository.getTodos().get(0);

		Calculadora.CalculadoraBuilder builder = Calculadora.CalculadoraBuilder
				.getInstance();
		
		Calculadora calc = builder.quantidadeConvites(quantidade).modeloConvite(m)
				.papelEnvelope(papelEnvelopeAplicado)
				.papelInterno(papelInternoAplicado)
				.colagem(colagem)
				.corte(corte)
				.impressaoEnvelope(impressaoEnvelopeAplicado)
				.impressaoInterno(impressaoInternoAplicado)
				.fita(fitaAplicada)
				.laco(lacoAplicado)
				.hotstamp(hotStampAplicado)
				.strass(strassAplicado, quantidadeStrass)
				.renda(rendaAplicada)
				.ima(imaAplicado)
				.impressaoNome(impressaoNomeAplicado)
				.serigrafiaEnvelope(serigrafiaAplicadaEnvelope)
				.serigrafiaInterno(serigrafiaAplicadaInterno).build();
		
		return calc.calcular();
	}
}
