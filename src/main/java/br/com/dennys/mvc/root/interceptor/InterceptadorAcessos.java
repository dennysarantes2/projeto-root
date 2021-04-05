package br.com.dennys.mvc.root.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;


public class InterceptadorAcessos implements WebRequestInterceptor{

	public static List<Acesso> acessos = new ArrayList<Acesso>();
		
	public static class Acesso{
		private String path;
		private LocalDateTime data;
		private Duration duracao;
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
		
		
	}
	
	@Override
	public void preHandle(WebRequest request) throws Exception {
		Acesso acesso = new Acesso();
		acesso.path = request.getContextPath();
		System.out.println("O path acessado: " + acesso.path);
		acesso.data = LocalDateTime.now();
		request.setAttribute("acesso", acesso, 0);		
	}


	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
		Acesso acesso = (Acesso)request.getAttribute("acesso", 0);
		acesso.duracao = Duration.between(acesso.data, LocalDateTime.now());
		acessos.add(acesso);
	}
	
}
