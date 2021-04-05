package br.com.dennys.mvc.root.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dennys.mvc.root.dto.RequisicaoEditarPedido;
import br.com.dennys.mvc.root.model.Pedido;
import br.com.dennys.mvc.root.model.StatusPedido;
import br.com.dennys.mvc.root.repository.PedidoRepository;


@RestController
@RequestMapping("/api/pedidos")
public class PedidosRest {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping("aguardando")
	public List<Pedido> getPedidosAguardandoOfertas(){		
		List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.AGUARDANDO);
		System.out.println(pedidos);
		
		return pedidos;
	}
	
	@GetMapping("{id}")
	public Pedido getPedidosAguardandoOfertas(@PathVariable("id") String id){		
		Pedido pedido = pedidoRepository.findById(Long.parseLong(id)).get();
		return pedido;
	}
	
	@PostMapping("editar")
	public String editarPedido(@RequestBody RequisicaoEditarPedido requisicaoEditarPedido) {
		
		
		Pedido pedido = pedidoRepository.findById(Long.parseLong(requisicaoEditarPedido.getId())).get();

		pedido = requisicaoEditarPedido.toPedido(pedido);
		
		pedidoRepository.save(pedido);

		return "redirect:/usuario/home";
	}
	
	
//	@GetMapping(value="aguardando/xml")
//	public XMLEncoder getPedidosAguardandoOfertasXml(){		
//		
//		List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.AGUARDANDO);	
//		XStream xstram = new XStream();
//		//xstram.alias("pedidos", Pedido.class); //Essa linha é necessária para omitir o caminho completo do pack da classe
//		String xml = xstram.toXML(pedidos);
//		
//		BufferedOutputStream bf;
//		try {
//			bf = new BufferedOutputStream(new FileOutputStream("Test.xml"));
//			XMLEncoder e = new XMLEncoder(bf);
//			e.writeObject(pedidos);
//			return e;
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}		
//		
//		
//		return null;
//		//return xstram.toXML(pedidos);
//	}
}
