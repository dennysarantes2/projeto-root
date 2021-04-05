package br.com.dennys.mvc.root.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import br.com.dennys.mvc.root.dto.RequisicaoEditarPedido;
import br.com.dennys.mvc.root.dto.RequisicaoNovoPedido;
import br.com.dennys.mvc.root.dto.storage.StorageFileNotFoundException;
import br.com.dennys.mvc.root.model.Pedido;
import br.com.dennys.mvc.root.model.Usuario;
import br.com.dennys.mvc.root.repository.PedidoRepository;
import br.com.dennys.mvc.root.repository.StorageService;


@Controller
@RequestMapping("pedido")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	private final StorageService storageService;
		
	@Autowired
	public PedidoController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping
	public ModelAndView home() {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String usernameLogado = ((UserDetails) principal).getUsername();
		List<Pedido> pedidos = pedidoRepository.findAll();
		//List<Pedido> pedidos = pedidoRepository.findByUser(usernameLogado);
		ModelAndView mv = new ModelAndView("home.html");
		mv.addObject("pedidos", pedidos);
		return mv;
	}
	
	@GetMapping("formulario")
	public String formulario(RequisicaoNovoPedido requisicaoNovoPedido, Model model) throws IOException {
		
		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(PedidoController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));
		
		return "pedido/formulario";
	}
	

	
	@PostMapping("novo")
	public String novo(@Valid RequisicaoNovoPedido requisicaoNovoPedido,
			BindingResult results
			,Principal principal
			,@RequestParam("file") MultipartFile file
			,RedirectAttributes redirectAttributes) {
		
		
		
		if (results.hasErrors()) {
			return "pedido/formulario";
		} else {
			Usuario user = new Usuario();
			user.setUsername(principal.getName());
			requisicaoNovoPedido.setUsername(user);
			requisicaoNovoPedido.setUrlImagem("images/" + file.getOriginalFilename());
			try {
				Pedido pedido = requisicaoNovoPedido.toPedido();
				pedidoRepository.save(pedido);
				storageService.store(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Sort sort = Sort.by("nomeProduto").descending();
			
			PageRequest of = PageRequest.of(0, 10, sort);
			
			List<Pedido> pedidos = pedidoRepository.findByUser(principal.getName(), of);
			ModelAndView mv = new ModelAndView("/usuario/home.html");
			mv.addObject("pedidos", pedidos);
			return "redirect:/usuario/home";
		}
	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@GetMapping("excluirPedido/{id}")
	public String excluirPedido(@PathVariable("id") String id) {
		
		
		try {
			pedidoRepository.deleteById(Long.parseLong(id));
		} catch (Exception e) {
			System.out.println("Ocorreu algum problema ao excluir o pedido.");
		}
		
		
		return "redirect:/usuario/home";
	}
	
	
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}

