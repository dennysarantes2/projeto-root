package br.com.dennys.mvc.root.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.server.reactive.ContextPathCompositeHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.dennys.mvc.root.dto.RequisicaoNovoUsuario;
import br.com.dennys.mvc.root.model.Pedido;
import br.com.dennys.mvc.root.model.StatusPedido;
import br.com.dennys.mvc.root.model.Usuario;
import br.com.dennys.mvc.root.repository.AuthorityRepository;
import br.com.dennys.mvc.root.repository.PedidoRepository;
import br.com.dennys.mvc.root.repository.UsuarioRepository;

@Controller
@RequestMapping(value={"","/","usuario"})
public class UsuarioController {

	 
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@RequestMapping(value = {"", "/home"})
	public String hello(Model model, Principal principal) {

		String username = principal.getName();
		Sort sort = Sort.by("nomeProduto").descending();
		PageRequest of = PageRequest.of(0, 8, sort);		
		List<Pedido> pedidos = pedidoRepository.findByUser(username, of);
		
		model.addAttribute("pedidos", pedidos);
		return "usuario/home";
	}
	
//	
	@GetMapping("/status/{status}")
	public ModelAndView status(@PathVariable("status") String status, Principal principal) {
		List<Pedido> pedidos = pedidoRepository
				.findByStatusAndUser(StatusPedido
				.valueOf(status.toUpperCase()), principal.getName());
		
		ModelAndView mv = new ModelAndView("usuario/home.html");
		mv.addObject("pedidos", pedidos);
		mv.addObject("status", status);
		return mv;
	}
	
	@GetMapping("/cadastro")
	public String cadastro(RequisicaoNovoUsuario requisicaoNovoUsuario) {
		return "usuario/cadastro";
	}
	
	@GetMapping(value = {"/images/{urlImagemLocal}", "/status/images/{urlImagemLocal}"})
	@ResponseBody
	public byte[] carregaImagensDinamicas(@PathVariable("urlImagemLocal") String nomeImagem) {
		
		File imagemArquivo = new File("./src/main/resources/templates/images/" + nomeImagem);
		
		try {
			return Files.readAllBytes(imagemArquivo.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
//	@GetMapping("/static/{nomeJs}")
//	@ResponseBody
//	public ModelAndView carregaJs(@PathVariable("nomeJs") String nomeJs) throws IOException {
//		
//		ModelAndView mv = new ModelAndView("usuario/home");
//		
//		File script = new File("./src/main/resources/static/" + nomeJs);
//		List<String> readAllLines = Files.readAllLines(script.toPath());
//		String scriptFinal = "";
//		
//		
//		for (String string : readAllLines) {
//			scriptFinal = scriptFinal + string;
//		}
//		System.out.println(scriptFinal);
//		mv.addObject(script);
//		return mv;
//		//return null;
//	}
	
	@PostMapping("/cadastro")
	public String cadastroNovoUsuario(@Valid RequisicaoNovoUsuario requisicaoNovoUsuario,
			BindingResult results, Principal principal, Model model) {
			
			Boolean usuarioExiste = verificaUsuario(requisicaoNovoUsuario);	
		
		if (results.hasErrors()) {
			return "usuario/cadastro";
		}
		
		if (usuarioExiste) {
			model.addAttribute("usuarioJaExistente", usuarioExiste);
			return "usuario/cadastro";
		}
		
		usuarioRepository.save(requisicaoNovoUsuario.toUsuario(principal.getName()));
		authorityRepository.save(requisicaoNovoUsuario.toAuthority());
		
		return "redirect:/usuario/home";
	}
	
	private Boolean verificaUsuario(RequisicaoNovoUsuario nomeUsuario) {
		
		Usuario usuarioCadastrado = usuarioRepository.findByUsername(nomeUsuario.getUsername());
		
		if (usuarioCadastrado != null) {
			return true;
		}
				
		return false;
	}

	
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/usuario/home";
	}
	
	
}
