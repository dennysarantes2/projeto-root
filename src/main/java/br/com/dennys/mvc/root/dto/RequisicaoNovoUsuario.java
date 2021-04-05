package br.com.dennys.mvc.root.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;

import br.com.dennys.mvc.root.model.Authority;
import br.com.dennys.mvc.root.model.Usuario;

public class RequisicaoNovoUsuario {
	
	@NotNull @NotEmpty(message = "Este campo não pode ficar em branco")
	private String username;
	@NotEmpty(message = "O preenchimento da senha é obrigatório")
	private String password;
	private Integer enabled;
	private String responsavelPeloCadastro;
	
	//@NotNull(message = "não pode ser nulo")
	@Size(min = 5 , message = "Escolha um dos valores disponíveis")
	private String authority;
	public String getUsername() {
		return username;
	}
	
	
	
	public String getResponsavelPeloCadastro() {
		return responsavelPeloCadastro;
	}



	public void setResponsavelPeloCadastro(String responsavelPeloCadastro) {
		this.responsavelPeloCadastro = responsavelPeloCadastro;
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
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enable) {
		this.enabled = enable;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public Usuario toUsuario(String responsavelPeloCadastro) {
		Usuario usuario = new Usuario();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		
		
		usuario.setEnabled(1);
		usuario.setUsername(this.username);
		usuario.setPassword(encoder.encode(password));
		usuario.setResponsavelPeloCadastro(responsavelPeloCadastro);
		
		return usuario;
		
	}
	
	public Authority toAuthority() {
		
		Authority authority = new Authority();
		
		authority.setAuthority(this.authority.replace(",", ""));
		authority.setUsername(this.username);
		
		return authority;
		
	}
}
