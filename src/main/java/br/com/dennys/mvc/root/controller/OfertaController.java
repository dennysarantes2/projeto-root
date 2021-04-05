package br.com.dennys.mvc.root.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oferta")
public class OfertaController {
	
	@GetMapping("/home2")
	public String home2() {
		return "/oferta/home2";
	}
	
	@GetMapping
	public String getFormularioParaOfertas() {
		return "/oferta/home";
	}
		
}
