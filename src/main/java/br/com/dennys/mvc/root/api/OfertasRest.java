package br.com.dennys.mvc.root.api;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dennys.mvc.root.dto.RequisicaoNovaOferta;
import br.com.dennys.mvc.root.model.Oferta;
import br.com.dennys.mvc.root.model.Pedido;
import br.com.dennys.mvc.root.repository.PedidoRepository;

@RestController
@RequestMapping("/api/ofertas")
public class OfertasRest {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	
	@PostMapping
	public Oferta criaOferta(@Valid @RequestBody RequisicaoNovaOferta requisicaoNovaOferta) {
		
		Optional<Pedido> pedidoBuscado = pedidoRepository.findById(requisicaoNovaOferta.getPedidoId());
		
		if (!pedidoBuscado.isPresent()) {
			System.out.println("True");
			return null;
		}
		
		
		Pedido pedido = pedidoBuscado.get();
		Oferta novaOferta = requisicaoNovaOferta.toOferta();
		novaOferta.setPedido(pedido);
		pedido.getOfertas().add(novaOferta);
		
		try {
			System.out.println("Salvando oferta....");
			pedidoRepository.save(pedido);
			System.out.println("Oferta salva com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return novaOferta;
	}
	
}
