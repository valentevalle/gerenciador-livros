package br.com.livros.api.dto.parser;

import static org.junit.Assert.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import br.com.livros.helpers.Stubs;

public class LivroDTOParserTest {
	
	@Test
	public void deve_Converter_Um_Modelo_De_Livro_Com_Sucesso() throws Exception {
		var livro = Stubs.livro("teste");
		var dto = LivroDTOParser.dto(livro);
		
		assertEquals(livro.getAutor(), dto.getAutor());
		assertEquals(livro.getDataPublicacao(), dto.getDataPublicacao());
		assertEquals(livro.getId(), dto.getId());
		assertEquals(livro.getIsbn(), dto.getIsbn());
		assertEquals(livro.getNome(), dto.getNome());
		assertEquals(livro.getPreco(), dto.getPreco());
		assertEquals(livro.getUrlImagemCapa(), dto.getUrlImagemCapa());
	}
	
	@Test
	public void deve_Converter_Uma_Lista_De_Modelos_De_Livro_Com_Sucesso() throws Exception {
		var livros = Stream.of(
						Stubs.livro("josé"), 
						Stubs.livro("joão"), 
						Stubs.livro("lucas"))
				.collect(Collectors.toList());
		
		var dtos = LivroDTOParser.dtos(livros);
		
		assertEquals(3, dtos.size());
		assertEquals("nome-livro-josé", dtos.get(0).getNome());
		assertEquals("nome-livro-joão", dtos.get(1).getNome());
		assertEquals("nome-livro-lucas", dtos.get(2).getNome());
	}
	
	@Test
	public void deve_Converter_Um_DTO_De_Livro_Com_Sucesso() throws Exception {
		var dto = Stubs.dto();
		var livro = LivroDTOParser.livro(dto);
		
		assertEquals(dto.getAutor(), livro.getAutor());
		assertEquals(dto.getDataPublicacao(), livro.getDataPublicacao());
		assertEquals(dto.getId(), livro.getId());
		assertEquals(dto.getIsbn(), livro.getIsbn());
		assertEquals(dto.getNome(), livro.getNome());
		assertEquals(dto.getPreco(), livro.getPreco());
		assertEquals(dto.getUrlImagemCapa(), livro.getUrlImagemCapa());
	}

}