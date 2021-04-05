package br.com.dennys.mvc.root.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import br.com.dennys.mvc.root.model.Pedido;
import br.com.dennys.mvc.root.model.Usuario;

public class OrganizarTodosHome {

	public void organizar(String teste ) {
		
		

		Sort sort = Sort.by("nomeProduto").descending();
		PageRequest of = PageRequest.of(0, 10, sort);
		//List<Pedido> pedidos = pedidoRepository.findByUser(principal.getName(), of);
		
	}
	
	public static void main(String[] args) {
		
		Usuario usuario = new Usuario();
		String nomeDaClasse = usuario.getClass().getSimpleName();
		
		switch (nomeDaClasse) {
		case "Usuario":
			System.out.println("Usuario");
			break;
		case "Pedido":
			System.out.println("Pedido");
		default:
			break;
		}
		
		if (nomeDaClasse.equals("Usuario")) {
			System.out.println("Certo!!");
		}
	}
	
}
