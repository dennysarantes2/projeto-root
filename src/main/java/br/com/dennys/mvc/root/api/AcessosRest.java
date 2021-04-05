package br.com.dennys.mvc.root.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.dennys.mvc.root.interceptor.InterceptorHandleAcessos;
import br.com.dennys.mvc.root.interceptor.InterceptorHandleAcessos.Acesso;
import br.com.dennys.mvc.root.model.Acessos;
import br.com.dennys.mvc.root.repository.AcessoRepository;


@RequestMapping("acessos")
@RestController
public class AcessosRest {

	@Autowired
	private AcessoRepository acessoRepository;
	
	@GetMapping
	public List<Acessos> getAcessos(){
			
		
		return acessoRepository.findAll();
	}
	
//	@GetMapping
//	public String getAcessos(){
//				
//		return "teste";
//	}
	
}
