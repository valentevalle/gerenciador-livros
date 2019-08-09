package br.com.livros.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.livros.api.dto.LivroDTO;
import br.com.livros.api.dto.parser.LivroDTOParser;
import br.com.livros.dominio.exception.IsbnJaCadastradoException;
import br.com.livros.dominio.exception.LivroNaoEncontradoException;
import br.com.livros.dominio.servico.ServicoDeDominioLivro;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controladora REST para manipulação de livros.
 * 
 * @author Rafael
 */
@RestController
@RequestMapping("/api/v1/livros")
@Api(description = "API REST para Livros",
		value = "Livros", 
		tags = { "Livros" })
public class EndpointLivros {

	@Autowired
	private ServicoDeDominioLivro servicoDeDominioLivro;
	
	public EndpointLivros() { }

	public EndpointLivros(ServicoDeDominioLivro servico) {
		this.servicoDeDominioLivro = servico;
	}

	/**
	 * POST para cadastro de livros.
	 * 
	 * @param livro - O livro a ser incluído.
	 * @return Os dados da inclusão.
	 * @throws IsbnJaCadastradoException - Caso já exista o referido ISBN no banco de dados.
	 */
	@PostMapping
	@ResponseBody
	@ApiOperation("Inclui um livro.")
	public ResponseEntity<LivroDTO> incluir(@RequestBody LivroDTO livro) throws IsbnJaCadastradoException {
		var entidade = LivroDTOParser.livro(livro);
		var livroIncluido = servicoDeDominioLivro.incluir(entidade);
		return ResponseEntity.ok(LivroDTOParser.dto(livroIncluido));
	}

	/**
	 * PUT para alteração de livros.
	 * 
	 * @param id    - O identificador do livro a ser alterado.
	 * @param livro - O livro a ser alterado.
	 * @return Os dados da alteração.
	 * @throws LivroNaoEncontradoException - Caso não seja encontrado livro com esse identificador.
	 * @throws IsbnJaCadastradoException   - Caso já exista o referido ISBN no banco de dados.
	 */
	@PutMapping("/{id}")
	@ResponseBody
	@ApiOperation("Altera um livro dado seu identificador.")
	public ResponseEntity<LivroDTO> alterar(@PathVariable long id, @RequestBody LivroDTO livro)
			throws LivroNaoEncontradoException, IsbnJaCadastradoException {
		var entidade = LivroDTOParser.livro(livro);
		var livroAlterado = servicoDeDominioLivro.alterar(id, entidade);
		return ResponseEntity.ok(LivroDTOParser.dto(livroAlterado));
	}

	/**
	 * DELETE para exclusão de livros.
	 * @param id - O identificador do livro a ser excluído.
	 * @throws LivroNaoEncontradoException - Caso não seja encontrado livro com esse identificador.
	 */
	@DeleteMapping("/{id}")
	@ApiOperation("Exclui um livro dado seu identificador.")
	public void excluir(@PathVariable long id) throws LivroNaoEncontradoException {
		var livro = servicoDeDominioLivro.buscarPorId(id);
		servicoDeDominioLivro.excluir(livro);
	}

	/**
	 * GET para listagem de livros.
	 * 
	 * @param atributo - O atributo para pesquisa.
	 * @param valor - O valor a ser pesquisado.
	 * @return A listagem de livros correspondente à pesquisa.
	 * @throws LivroNaoEncontradoException - Caso não seja encontrado livro com esses parâmetros de pesquisa.
	 */
	@GetMapping("/{atributo}/{valor}")
	@ResponseBody
	@ApiOperation("Pesquisa livros por seus atributos.")
	public ResponseEntity<List<LivroDTO>> listarPorAtributo(
			@PathVariable String atributo,
			@PathVariable String valor,
			@RequestParam("ordem") String ordem) throws LivroNaoEncontradoException {
		var ordenacao = (ordem == null || ordem == "") ? "id" : ordem;
		var livros = servicoDeDominioLivro.buscar(atributo, valor, ordenacao);
		return ResponseEntity.ok(LivroDTOParser.dtos(livros));
	}
	
}