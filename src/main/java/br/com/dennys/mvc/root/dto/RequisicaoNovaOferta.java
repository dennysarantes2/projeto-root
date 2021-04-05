package br.com.dennys.mvc.root.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.dennys.mvc.root.model.Oferta;

public class RequisicaoNovaOferta {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@NotNull
	private Long pedidoId;
	//necessário consultar a sintaxe do regexp
	@Pattern(regexp = "^\\d+(\\.\\d+{2})?$", message = "Insira um valor válido R$ +00,00")
	@NotNull(message = "A oferta precisa de um valor")
	private String valor;
	
	@Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}$", message = "Formato de data válido: dd/mm/aaaa")
	@NotNull(message = "A data precisa ser informada")
	private String data;
	
	private String comentario;
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public Oferta toOferta() {
		Oferta oferta = new Oferta();
		oferta.setComentario(this.comentario);
		oferta.setDataDaEntrega(LocalDate.parse(this.data, formatter));
		oferta.setValor(new BigDecimal(this.valor));
		
		return oferta;
	}
}
