package br.com.dennys.mvc.root.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.dennys.mvc.root.model.Acessos;
import br.com.dennys.mvc.root.repository.AcessoRepository;

@Component
public class InterceptorHandleAcessos implements HandlerInterceptor{

	public static List<Acesso> acessos = new ArrayList<Acesso>();
	
	@Autowired
	private AcessoRepository acessoRepository;
		
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("--------------In preHandle - Intercept ----------");
		Acesso acesso = new Acesso();
		acesso.path = request.getRequestURI();
		System.out.println("path solicitado é " + acesso.path );
		acesso.data = LocalDateTime.now();
		try {
			acesso.user = request.getUserPrincipal().getName();
		} catch (Exception e) {
			acesso.user = "anonymus";
		}
		
		
		request.setAttribute("acesso", acesso);	
		
		return true;
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
				System.out.println("--------------In afterCompletion - Intercept ----------");
				Acesso acesso = (Acesso) request.getAttribute("acesso");
				acesso.duracao = Duration.between(acesso.data, LocalDateTime.now());
				acesso.tempoProcessamentoSegundos =  Duration.between(acesso.data, LocalDateTime.now()).getSeconds();
				//acessos.add(acesso);
				Acessos acessos = acesso.AcessoToAcessos();
				
				try {
					acessoRepository.save(acessos);
					System.out.println("Acesso gravado em log");
				} catch (Exception e) {
					System.out.println("falha ao gravar log");
				}
				
				if (response.getStatus() == 403) {
					response.sendRedirect("/usuario/home");
				}
				
				System.out.println(response.getStatus());
	}
	
	public static class Acesso{
		private String path;
		private LocalDateTime data;
		private Duration duracao;
		private Long tempoProcessamentoSegundos;
		private String user;
		
		
		public Long getTempoProcessamentoSegundos() {
			return tempoProcessamentoSegundos;
		}
		public void setTempoProcessamentoSegundos(Long tempoProcessamentoSegundos) {
			this.tempoProcessamentoSegundos = tempoProcessamentoSegundos;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public LocalDateTime getData() {
			return data;
		}
		public void setData(LocalDateTime data) {
			this.data = data;
		}
		public Duration getDuracao() {
			return duracao;
		}
		public void setDuracao(Duration duracao) {
			this.duracao = duracao;
		}
		
		public Acessos AcessoToAcessos() {
			Acessos acessos = new Acessos();
			acessos.setData(this.data);
			acessos.setDuracao(this.duracao);
			acessos.setTempoProcessamentoSegundos(this.tempoProcessamentoSegundos);
			acessos.setPath(this.path);
			acessos.setUser(this.user);
			return acessos;	
		}
		
	}
}
