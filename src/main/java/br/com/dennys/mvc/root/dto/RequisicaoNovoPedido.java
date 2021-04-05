package br.com.dennys.mvc.root.dto;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import br.com.dennys.mvc.root.model.Pedido;
import br.com.dennys.mvc.root.model.StatusPedido;
import br.com.dennys.mvc.root.model.Usuario;

public class RequisicaoNovoPedido {
	@NotBlank(message = "O nome do produto é obrigatório!")
	String nomePedido;
	@NotBlank(message = "A url é obrigatória, favor preencher.")
	private String urlProduto;
	//@NotBlank(message = "Ter uma imagem do produto é algo importante, favor preencher!")
	private String urlImagem;
	private String descricao;
	private Usuario username;
	
	
	public Usuario getUsername() {
		return username;
	}
	public void setUsername(Usuario username) {
		this.username = username;
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
	public String getUrlImagem() {
		return urlImagem;
	}
	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Pedido toPedido() {
		Pedido pedido = new Pedido();
		
		pedido.setDescricao(descricao);
		pedido.setNomeProduto(nomePedido);
		//pedido.setUrlImagemExterna(urlImagem);
		pedido.setUrlImagemLocal(urlImagem);
		pedido.setUrlProduto(urlProduto);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setUser(username);
		return pedido;
	}
	
	
}
