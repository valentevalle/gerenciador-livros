package br.com.livros.dominio.exception;

/**
 * Exception para livros não encontrados de acordo com o critério de pesquisa.
 * 
 * @author Rafael
 */
public class LivroNaoEncontradoException extends Exception {

	private static final long serialVersionUID = -2493485431691597667L;

	/**
	 * {@inheritDoc}
	 */
	public LivroNaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * {@inheritDoc}
	 */
	public LivroNaoEncontradoException(String message) {
		super(message);
	}

	/**
	 * {@inheritDoc}
	 */
	public LivroNaoEncontradoException(Throwable cause) {
		super(cause);
	}

}