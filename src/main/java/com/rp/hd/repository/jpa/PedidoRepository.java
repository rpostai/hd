package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.Pedido;

@Stateless
public class PedidoRepository extends BaseRepository<Pedido> {

	public PedidoRepository() {
		super(Pedido.class);
	}

}
