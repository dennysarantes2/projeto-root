package br.com.dennys.mvc.root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dennys.mvc.root.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority , String> {

}
