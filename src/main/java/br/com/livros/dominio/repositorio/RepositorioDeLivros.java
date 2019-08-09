package br.com.livros.dominio.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.livros.dominio.modelo.Livro;

/**
 * Interface para repositório de livros, utilizando abstração 
 * de JPA do Spring.
 * 
 * @author Rafael
 */
@Repository
public interface RepositorioDeLivros extends JpaRepository<Livro, Long> {
	
}