package com.rp.hd.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class HotStamp extends BaseEntity {

	@ElementCollection
	@CollectionTable(name = "hot_stamp_precos")
	private List<PrecoVigencia> precos = new ArrayList<>();

	public List<PrecoVigencia> getPrecos() {
		return precos;
	}

	public void addPreco(PrecoVigencia preco) {
		this.precos.add(preco);
	}

}
