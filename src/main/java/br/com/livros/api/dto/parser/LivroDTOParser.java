package br.com.livros.api.dto.parser;

import java.util.List;
import java.util.stream.Collectors;

import br.com.livros.api.dto.LivroDTO;
import br.com.livros.dominio.modelo.Livro;

/**
 * Parser para traduzir o modelo de Livro para o respectivo DTO e vice-versa.
 * Ver também: {@link Livro} e {@link LivroDTO}
 * 
 * @author Rafael
 */
public class LivroDTOParser {

	/**
	 * Traduz um DTO de Livro no respectivo modelo de domínio.
	 * 
	 * @param dto - O DTO a ser traduzido.
	 * @return o modelo de domínio preenchido.
	 */
	public static Livro livro(LivroDTO dto) {
		return Livro.builder()
				.id(dto.getId())
				.autor(dto.getAutor())
				.dataPublicacao(dto.getDataPublicacao())
				.isbn(dto.getIsbn())
				.nome(dto.getNome())
				.preco(dto.getPreco())
				.urlImagemCapa(dto.getUrlImagemCapa())
				.build();
	}

	/**
	 * Traduz um modelo de domínio de Livro no respectivo DTO.
	 * 
	 * @param livro - O modelo de domínio a ser traduzido.
	 * @return o DTO preeenchido.
	 */
	public static LivroDTO dto(Livro livro) {
		return LivroDTO.builder()
				.id(livro.getId())
				.autor(livro.getAutor())
				.dataPublicacao(livro.getDataPublicacao())
				.isbn(livro.getIsbn())
				.nome(livro.getNome())
				.preco(livro.getPreco())
				.urlImagemCapa(livro.getUrlImagemCapa())
				.build();
	}

	/**
	 * Traduz uma lista de modelos de domínio de livros nos respectivos DTOs.
	 * 
	 * @param livros - Os modelos de domínio a serem traduzidos.
	 * @return os DTOs preenchidos.
	 */
	public static List<LivroDTO> dtos(List<Livro> livros) {
		return livros.stream()
				.map(LivroDTOParser::dto)
				.collect(Collectors.toList());
	}

}