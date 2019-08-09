package br.com.livros.api.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de Livro para uso nos Endpoints da aplicação.
 * 
 * @author Rafael
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

	private Long id;
	private String isbn;
	private String autor;
	private String nome;
	private Double preco;
	private String urlImagemCapa;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataPublicacao;

}