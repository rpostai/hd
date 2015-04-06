package com.rp.hd.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String cliente;

	private String rg;

	private String cpf;

	private String endereco;

	private String numero;

	private String bairro;

	private String cidade;

	private String telefone;

	private Date dataEvento;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="pedido")
	private List<PedidoItem> itens;

	private BigDecimal valorTotalPedido;

	private String observacoes;

	private Date dataPrevistaEntrega;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="pedido")
	private List<PedidoFormaPagamento> pagamentos;

	public Pedido() {

	}

	private Pedido(String cliente, String rg, String cpf, String endereco,
			String numero, String bairro, String cidade, String telefone,
			Date dataEvento, List<PedidoItem> itens,
			BigDecimal valorTotalPedido, String observacoes,
			Date dataPrevistaEntrega, List<PedidoFormaPagamento> pagamentos) {
		this();
		this.cliente = cliente;
		this.rg = rg;
		this.cpf = cpf;
		this.endereco = endereco;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.telefone = telefone;
		this.dataEvento = dataEvento;
		this.itens = itens;
		this.valorTotalPedido = valorTotalPedido;
		this.observacoes = observacoes;
		this.dataPrevistaEntrega = dataPrevistaEntrega;
		this.pagamentos = pagamentos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public List<PedidoItem> getItens() {
		return itens;
	}

	public void setItens(List<PedidoItem> itens) {
		this.itens = itens;
	}

	public BigDecimal getValorTotalPedido() {
		return valorTotalPedido;
	}

	public void setValorTotalPedido(BigDecimal valorTotalPedido) {
		this.valorTotalPedido = valorTotalPedido;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Date getDataPrevistaEntrega() {
		return dataPrevistaEntrega;
	}

	public void setDataPrevistaEntrega(Date dataPrevistaEntrega) {
		this.dataPrevistaEntrega = dataPrevistaEntrega;
	}

	public List<PedidoFormaPagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<PedidoFormaPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public static class PedidoBuilder {

		private String cliente;
		private String rg;
		private String cpf;
		private String endereco;
		private String numero;
		private String bairro;
		private String cidade;
		private String telefone;
		private Date dataEvento;
		private List<PedidoItem> itens;
		private String observacoes;
		private Date dataPrevistaEntrega;
		private List<PedidoFormaPagamento> pagamentos;

		public PedidoBuilder setCliente(String cliente) {
			this.cliente = cliente;
			return this;
		}

		public PedidoBuilder setRg(String rg) {
			this.rg = rg;
			return this;
		}

		public PedidoBuilder setCpf(String cpf) {
			this.cpf = cpf;
			return this;
		}

		public PedidoBuilder setEndereco(String endereco) {
			this.endereco = endereco;
			return this;
		}

		public PedidoBuilder setNumero(String numero) {
			this.numero = numero;
			return this;
		}

		public PedidoBuilder setBairro(String bairro) {
			this.bairro = bairro;
			return this;
		}

		public PedidoBuilder setCidade(String cidade) {
			this.cidade = cidade;
			return this;
		}

		public PedidoBuilder setTelefone(String telefone) {
			this.telefone = telefone;
			return this;
		}

		public PedidoBuilder setDataEvento(Date dataEvento) {
			this.dataEvento = dataEvento;
			return this;
		}

		public PedidoBuilder setItens(List<PedidoItem> itens) {
			this.itens = itens;
			return this;
		}

		public PedidoBuilder setObservacoes(String observacoes) {
			this.observacoes = observacoes;
			return this;
		}

		public PedidoBuilder setDataPrevistaEntrega(Date dataPrevistaEntrega) {
			this.dataPrevistaEntrega = dataPrevistaEntrega;
			return this;
		}

		public PedidoBuilder setPagamentos(List<PedidoFormaPagamento> pagamentos) {
			this.pagamentos = pagamentos;
			return this;
		}

		public Pedido build() {
			if (StringUtils.isBlank(cliente)) {
				throw new IllegalArgumentException(
						"O campo cliente é obrigatório");
			}
			if (StringUtils.isBlank(rg)) {
				throw new IllegalArgumentException("O campo rg é obrigatório");
			}
			if (StringUtils.isBlank(cpf)) {
				throw new IllegalArgumentException("O campo cpf é obrigatório");
			}
			if (StringUtils.isBlank(endereco)) {
				throw new IllegalArgumentException("O endereco é obrigatório");
			}
			if (StringUtils.isBlank(numero)) {
				throw new IllegalArgumentException(
						"O campo numero é obrigatório");
			}
			if (StringUtils.isBlank(bairro)) {
				throw new IllegalArgumentException(
						"O campo bairro é obrigatório");
			}
			if (StringUtils.isBlank(cidade)) {
				throw new IllegalArgumentException(
						"O campo cidade é obrigatório");
			}
			if (StringUtils.isBlank(telefone)) {
				throw new IllegalArgumentException(
						"O campo telefone é obrigatório");
			}
			if (dataEvento == null) {
				throw new IllegalArgumentException(
						"O campo data do evento é obrigatório");
			}
			if (dataPrevistaEntrega == null) {
				throw new IllegalArgumentException(
						"O campo data prevista de entrega é obrigatório");
			}
			if (CollectionUtils.isEmpty(itens)) {
				throw new IllegalArgumentException(
						"É obrigatório pelo menos 1 item para a geração do pedido");
			}
			if (CollectionUtils.isEmpty(pagamentos)) {
				throw new IllegalArgumentException(
						"É obrigatório definir a forma de pagamento");
			}

			Optional<BigDecimal> valorTotalPedido = itens.stream().map(x -> {
				return x.getValorUnidadeFinal();
			}).reduce((x, y) -> {
				return x.add(y);
			});

			return new Pedido(cliente, rg, cpf, endereco, numero, bairro,
					cidade, telefone, dataEvento, itens,
					valorTotalPedido.get(), observacoes, dataPrevistaEntrega,
					pagamentos);
		}

	}

}
