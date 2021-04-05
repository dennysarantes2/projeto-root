package br.com.dennys.mvc.root.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Usuario {
	
	@Id
	private String username;
	private String password;
	private Integer enabled;
	private String responsavelPeloCadastro;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private List<Pedido> pedidos;

	
	
	
	public String getResponsavelPeloCadastro() {
		return responsavelPeloCadastro;
	}



	public void setResponsavelPeloCadastro(String responsavelPeloCadastro) {
		this.responsavelPeloCadastro = responsavelPeloCadastro;
	}



	public Integer getEnabled() {
		return enabled;
	}



	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}



	public String getUsername() {
		return username;
	}

	
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	
	
}
