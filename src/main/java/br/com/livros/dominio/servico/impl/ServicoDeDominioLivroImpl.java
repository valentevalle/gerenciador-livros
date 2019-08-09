package br.com.livros.dominio.servico.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.livros.dominio.exception.IsbnJaCadastradoException;
import br.com.livros.dominio.exception.LivroNaoEncontradoException;
import br.com.livros.dominio.modelo.Livro;
import br.com.livros.dominio.repositorio.RepositorioDeLivros;
import br.com.livros.dominio.servico.ServicoDeDominioLivro;
import br.com.livros.dominio.servico.impl.busca.ManipuladorDeBuscaDeLivro;

/**
 * Implementação padrão de serviço de domínio de livro.
 * 
 * @author Rafael
 */
@Service
public class ServicoDeDominioLivroImpl implements ServicoDeDominioLivro {

	@Autowired
	private RepositorioDeLivros repositorioDeLivros;

	/**
	 * Valida se um livro já existe dado seu ISBN.
	 * 
	 * @param livro
	 * @return
	 * @throws IsbnJaCadastradoException
	 */
	private List<Livro> buscarPorIsbn(String isbn) throws IsbnJaCadastradoException {
		var livroPesquisa = Livro.builder().isbn(isbn).build();
		var exemplo = Example.of(livroPesquisa);
		return repositorioDeLivros.findAll(exemplo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Livro incluir(Livro livro) throws IsbnJaCadastradoException {
		var livros = buscarPorIsbn(livro.getIsbn());

		if (!livros.isEmpty()) {
			throw new IsbnJaCadastradoException("Já foi cadastrado livro com esse ISBN.");
		}

		return repositorioDeLivros.saveAndFlush(livro);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Livro alterar(Long id, Livro livro) throws LivroNaoEncontradoException, IsbnJaCadastradoException {
		Livro livroEmAlteracao = repositorioDeLivros.findById(id)
				.orElseThrow(() -> new LivroNaoEncontradoException("Não foi encontrado livro com esse identificador."));

		var livros = buscarPorIsbn(livro.getIsbn());
		livros.remove(livroEmAlteracao);
		if (!livros.isEmpty()) {
			throw new IsbnJaCadastradoException("Já foi cadastrado livro com esse ISBN.");
		}

		livroEmAlteracao.setAutor(livro.getAutor());
		livroEmAlteracao.setDataPublicacao(livro.getDataPublicacao());
		livroEmAlteracao.setIsbn(livro.getIsbn());
		livroEmAlteracao.setNome(livro.getNome());
		livroEmAlteracao.setPreco(livro.getPreco());
		livroEmAlteracao.setUrlImagemCapa(livro.getUrlImagemCapa());

		return repositorioDeLivros.saveAndFlush(livroEmAlteracao);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void excluir(Livro livro) {
		repositorioDeLivros.delete(livro);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Livro buscarPorId(Long id) throws LivroNaoEncontradoException {
		var livro = repositorioDeLivros.findById(id);
		return livro.orElseThrow(
				() -> new LivroNaoEncontradoException("Não foi encontrado livro com esse identificador"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Livro> buscar(String atributo, String valor, String ordenacao) throws LivroNaoEncontradoException {
		var builder = Livro.builder();
		var manipuladorId = new ManipuladorDeBuscaDeLivro("id", v -> builder.id(Long.parseLong(v)));
		var manipuladorIsbn = new ManipuladorDeBuscaDeLivro("isbn", v -> builder.isbn(v), manipuladorId);
		var manipuladorAutor = new ManipuladorDeBuscaDeLivro("autor", v -> builder.autor(v), manipuladorIsbn);
		var manipuladorNome = new ManipuladorDeBuscaDeLivro("nome", v -> builder.nome(v), manipuladorAutor);
		var manipuladorPreco = new ManipuladorDeBuscaDeLivro("preco", v -> builder.preco(Double.parseDouble(v)),
				manipuladorNome);
		var manipuladorUrlImagemCapa = new ManipuladorDeBuscaDeLivro("urlImagemCapa", v -> builder.urlImagemCapa(v),
				manipuladorPreco);
		var manipuladorDataPublicacao = new ManipuladorDeBuscaDeLivro("dataPublicacao", v -> {
			try {
				builder.dataPublicacao(new SimpleDateFormat("yyyy-MM-dd").parse(v));
			} catch (ParseException e) {
				builder.dataPublicacao(Calendar.getInstance().getTime());
			}
		}, manipuladorUrlImagemCapa);

		manipuladorDataPublicacao.manipular(atributo, valor);

		var exemplo = Example.of(builder.build());
		var ordem = Sort.by(Sort.Direction.ASC, ordenacao);
		var retorno = repositorioDeLivros.findAll(exemplo, ordem);

		if (retorno.isEmpty()) {
			throw new LivroNaoEncontradoException("Não foi encontrado livro com os parâmetros informados.");
		}

		return retorno;
	}

}