package br.com.livros.helpers;

import br.com.livros.api.dto.LivroDTO;
import br.com.livros.dominio.modelo.Livro;

public abstract class Stubs {
	
	public static Livro livro(String nome) {
		return Livro.builder()
				.autor("autor-livro")
				.isbn("isbn-livro")
				.nome("nome-livro-" + nome)
				.preco(1.99D)
				.urlImagemCapa("imagem-livro")
				.build();
	}
	
	public static LivroDTO dto() {
		return LivroDTO.builder()
				.autor("autor-dto")
				.id(2L)
				.isbn("isbn-dto")
				.nome("nome-dto")
				.preco(2.99D)
				.urlImagemCapa("imagem-dto")
				.build();
	}

}
