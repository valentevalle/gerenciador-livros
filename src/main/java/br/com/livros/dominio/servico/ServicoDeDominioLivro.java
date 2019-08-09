package br.com.livros.dominio.servico;

import java.util.List;

import br.com.livros.dominio.exception.IsbnJaCadastradoException;
import br.com.livros.dominio.exception.LivroNaoEncontradoException;
import br.com.livros.dominio.modelo.Livro;

/**
 * Interface para servico de domínio de livros.
 * 
 * @author Rafael
 */
public interface ServicoDeDominioLivro {

	/**
	 * Valida se um livro já existe por ISBN e inclui caso não exista.
	 * 
	 * @param livro - O livro a ser incluído.
	 * @return Entidade com as informações.
	 * @throws IsbnJaCadastradoException - Caso já exista o referido ISBN no banco de dados.
	 */
	Livro incluir(Livro livro) throws IsbnJaCadastradoException;

	/**
	 * Valida se um livro já existe com o novo ISBN informado e altera caso não
	 * exista.
	 * 
	 * @param id    - O identificador do livro a ser alterado.
	 * @param livro - O livro a ser incluído.
	 * @return Entidade com as informações.
	 * @throws IsbnJaCadastradoException   - Caso já exista o referido ISBN no banco de dados.
	 * @throws LivroNaoEncontradoException - Caso não seja encontrado livro com esse identificador.
	 */
	Livro alterar(Long id, Livro livro) throws LivroNaoEncontradoException, IsbnJaCadastradoException;

	/**
	 * Exclui um livro, caso o mesmo exista.
	 * 
	 * @param livro - O livro a ser excluído.
	 */
	void excluir(Livro livro);

	/**
	 * Busca um livro pelo seu identificador.
	 * 
	 * @param id - O identificador do livro a ser excluído.
	 * @return O livro com o identificador desejado.
	 * @throws LivroNaoEncontradoException - Caso não seja encontrado livro com esse identificador.
	 */
	Livro buscarPorId(Long id) throws LivroNaoEncontradoException;
	
	/**
	 * Busca uma relação de livros por qualquer um de seus atributos.
	 * 
	 * @param atributo  - O nome do atributo para busca.
	 * @param valor     - O valor a ser pesquisado. 
	 * @param ordenacao - O atributo pelo qual a lista deve ser ordenada.
	 * @return A listagem de livros.
	 * @throws LivroNaoEncontradoException - Caso não seja encontrado livro com esses parâmetros de pesquisa.
	 */
	List<Livro> buscar(String atributo, String valor, String ordenacao) throws LivroNaoEncontradoException;

}