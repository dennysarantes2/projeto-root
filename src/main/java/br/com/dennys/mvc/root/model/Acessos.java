package br.com.dennys.mvc.root.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Acessos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private String path;
	private LocalDate data;
	private Duration duracao;
	private Long tempoProcessamentoSegundos;
	private String user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		//DateTimeFormatter dt2 = DateTimeFormatter.ISO_LOCAL_DATE;
		this.data = LocalDate.of(data.getYear(), data.getMonth(), data.getDayOfMonth());
	}
	public Duration getDuracao() {
		return duracao;
	}
	public void setDuracao(Duration duracao) {
		this.duracao = duracao;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setTempoProcessamentoSegundos(Long tempoProcessamentoSegundos) {
		this.tempoProcessamentoSegundos = tempoProcessamentoSegundos;
		
	}
	
	
	
}
