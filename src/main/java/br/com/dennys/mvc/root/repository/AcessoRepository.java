package br.com.dennys.mvc.root.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dennys.mvc.root.model.Acessos;

@Repository
public interface AcessoRepository extends JpaRepository<Acessos, Long> {

	List<Acessos> findAll();
}
