package br.com.dennys.mvc.root.repository;


import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dennys.mvc.root.model.Pedido;
import br.com.dennys.mvc.root.model.StatusPedido;


@Repository
public interface PedidoRepository extends  JpaRepository<Pedido, Long> {

	
	List<Pedido> findAll();
	
	List<Pedido> findByStatus(StatusPedido status);
	
	@Cacheable("consultaUsuario")
	@Query("select p from Pedido p join p.user u where u.username = :pUser")
	List<Pedido> findByUser(@Param("pUser")String pUser, Pageable pr);
	
	@Query(value = "select p"
			+ " from Pedido p "
			+ "join p.user u "
			+ "where u.username = :pUser "
			+ "and p.status = :pStatus")
	List<Pedido> findByStatusAndUser(StatusPedido pStatus, String pUser);
}
