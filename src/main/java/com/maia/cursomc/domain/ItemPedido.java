package com.maia.cursomc.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId // Id imbutido em Uma Class Auxiliar
	private ItemPedidoPK id = new ItemPedidoPK();

	private Double descont;
	private Integer quantidade;
	private Double preco;

	// ***Construtores***//
	public ItemPedido() {
	}

	public ItemPedido(Pedido pedido, Produto produto, Double descont, Integer quantidade, Double preco) {
		super();
		id.setPedido(pedido);
		id.setProduto(produto);
		this.descont = descont;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	// calculando o Sub Total do Pedido * obs.: usar o get... para ser serealizado
	// pelo Json
	public double getSubTotal() {
		return (preco - descont) * quantidade;

	}

	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}

	// ligando i tem ao pedido no momento da instancia do Obj
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}

	public Produto getProduto() {
		return id.getProduto();
	}

	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	// **Geteres e Setters**//
	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDescont() {
		return descont;
	}

	public void setDescont(Double descont) {
		this.descont = descont;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
