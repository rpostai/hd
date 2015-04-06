package com.rp.hd.domain;

import java.util.Arrays;
import java.util.Optional;

public enum Gramatura {

	G50(50),G120(120), G180(180), G240(240), G250(250), G300(300), G170(170), G220(220),G320(320);

	private final int valor;
	
	private Gramatura(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}
	
	public static Gramatura getGramatura(Integer valor) {
		Optional<Gramatura> result = Arrays.asList(values()).stream()
				.filter(x -> {
					return valor.equals(x.getValor());
				}).findFirst();
		return result.get();
	}

}
