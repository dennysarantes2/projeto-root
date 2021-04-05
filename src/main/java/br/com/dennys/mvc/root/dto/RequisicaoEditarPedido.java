package br.com.dennys.mvc.root.dto;

import br.com.dennys.mvc.root.model.Pedido;

public class RequisicaoEditarPedido {

	private String id;
	private String nomePedido;
	private String urlProduto;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNomePedido() {
		return nomePedido;
	}
	public void setNomePedido(String nomePedido) {
		this.nomePedido = nomePedido;
	}
	public String getUrlProduto() {
		return urlProduto;
	}
	public void setUrlProduto(String urlProduto) {
		this.urlProduto = urlProduto;
	}
	public Pedido toPedido(Pedido pedido) {
		pedido.setNomeProduto(this.nomePedido);
		pedido.setUrlProduto(this.urlProduto);
				
		return pedido;
	}
	
	
	
}
