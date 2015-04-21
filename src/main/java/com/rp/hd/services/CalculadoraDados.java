package com.rp.hd.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rp.hd.domain.Cliche;
import com.rp.hd.domain.Fita;
import com.rp.hd.domain.HotStamp;
import com.rp.hd.domain.Ima;
import com.rp.hd.domain.Impressao;
import com.rp.hd.domain.ImpressaoNome;
import com.rp.hd.domain.Laco;
import com.rp.hd.domain.ModeloConvite;
import com.rp.hd.domain.OrigemContato;
import com.rp.hd.domain.Papel;
import com.rp.hd.domain.Renda;
import com.rp.hd.domain.Serigrafia;
import com.rp.hd.domain.Strass;

public class CalculadoraDados implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -495454109364243671L;
	
	private List<Dado> origemContato = new ArrayList<Dado>();

	private List<Dado> papeis = new ArrayList<>();
	
	private List<Dado> fitas = new ArrayList<>();
	
	private List<Dado> lacos = new ArrayList<>();
	
	private List<Dado> imas = new ArrayList<>();
	
	private List<Dado> impressoes = new ArrayList<>();
	
	private List<Dado> impressoesNome = new ArrayList<>();
	
	private List<Dado> modelos = new ArrayList<>();
	
	private List<Dado> rendas = new ArrayList<>();
	
	private List<Dado> strass = new ArrayList<>();
	
	private List<Dado> serigrafia= new ArrayList<>();
	
	private List<Dado> hotstamps= new ArrayList<>();
	
	private List<Dado> cliches = new ArrayList<>();
	
	public List<Dado> getPapeis() {
		return papeis;
	}

	public List<Dado> getFitas() {
		return fitas;
	}

	public List<Dado> getLacos() {
		return lacos;
	}

	public List<Dado> getImas() {
		return imas;
	}

	public List<Dado> getImpressoes() {
		return impressoes;
	}

	public List<Dado> getImpressoesNome() {
		return impressoesNome;
	}

	public List<Dado> getModelos() {
		return modelos;
	}

	public List<Dado> getRendas() {
		return rendas;
	}

	public List<Dado> getStrass() {
		return strass;
	}

	public List<Dado> getSerigrafia() {
		return serigrafia;
	}

	public List<Dado> getHotstamps() {
		return hotstamps;
	}
	
	public List<Dado> getCliches() {
		return cliches;
	}

	private void add(List<Dado> list, Dado dado) {
		list.add(dado);
	}
	
	private Dado novoDado(Long id, String descricao) {
		Dado dado = new Dado();
		dado.setId(id);
		dado.setNome(descricao);
		return dado;
	}
	
	public void addCliche(Cliche cliche) {
		this.add(cliches, novoDado(cliche.getId(), cliche.getDescricao()));
	}
	
	public void addPapel(Papel papel) {
		this.add(papeis, novoDado(papel.getId(), papel.getNome()));
	}
	
	public void addModelo(ModeloConvite modelo) {
		this.add(modelos, novoDado(modelo.getId(), modelo.getNome()));
	}
	
	public void addIma(Ima ima) {
		this.add(imas, novoDado(ima.getId(), ima.getDescricao()));
	}
	
	public void  addImpressao(Impressao impressao) {
		this.add(impressoes, novoDado(impressao.getId(), impressao.getDescricao()));
	}
	
	public void addSerigrafia(Serigrafia serigrafia) {
		this.add(this.serigrafia, novoDado(serigrafia.getId(), serigrafia.getDescricao()));
	}
	
	public void addLaco(Laco laco) {
		this.add(this.lacos, novoDado(laco.getId(), laco.getDescricao()));
	}
	
	public void addHotStamp(HotStamp hotStamp) {
		this.add(this.hotstamps, novoDado(hotStamp.getId(),hotStamp.getDescricao()));
	}
	
	public void addFita(Fita fita) {
		this.add(this.fitas, novoDado(fita.getId(),fita.toString()));
	}
	
	public void addStrass(Strass strass) {
		this.add(this.strass, novoDado(strass.getId(),strass.toString()));
	}
	
	public void addRenda(Renda renda) {
		this.add(this.rendas, novoDado(renda.getId(),renda.getDescricao()));
	}
	
	public void addImpressaoNome(ImpressaoNome imp) {
		this.add(this.impressoesNome, novoDado(imp.getId(),imp.getDescricao()));
	}
	
	public void addOrigemContato(OrigemContato origem) {
		origemContato.add(novoDado(origem.getId(), origem.getDescricao()));
	}

	public List<Dado> getOrigemContato() {
		return origemContato;
	}
	
}
