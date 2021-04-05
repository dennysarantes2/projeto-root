package br.com.dennys.mvc.root.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.dennys.mvc.root.model.Pedido;
import br.com.dennys.mvc.root.repository.PedidoRepository;

@Controller
public class LoginController {

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping
	@RequestMapping("/login")
	public ModelAndView login(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView mv = new ModelAndView();
		if (principal instanceof UserDetails) {
			System.out.println("usuário logado " + ((UserDetails) principal).getUsername());
			List<Pedido> pedidos = pedidoRepository.findAll();
			mv.addObject("pedidos", pedidos);
			mv.setViewName("home");
			return mv;
		} else {
			System.out.println("usuário não logado " + principal.toString());
			mv.setViewName("login");
		}
		// model.addAttribute("logado", "true");
		return mv;
	}

//	@GetMapping
//	@RequestMapping("/logout")
//	public ModelAndView logout() {
//		ModelAndView mv = new ModelAndView("login");		
//		//mv.addObject("logado", "true");
//		return mv;
//	}

}
