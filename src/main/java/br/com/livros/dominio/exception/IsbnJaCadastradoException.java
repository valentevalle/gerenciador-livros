package br.com.livros.dominio.exception;

/**
 * Exception para indicar livros já cadastrados.
 * 
 * @author Rafael
 */
public class IsbnJaCadastradoException extends Exception {

	private static final long serialVersionUID = 9076623867480682131L;

	/**
	 * {@inheritDoc}
	 */
	public IsbnJaCadastradoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * {@inheritDoc}
	 */
	public IsbnJaCadastradoException(String message) {
		super(message);
	}

	/**
	 * {@inheritDoc}
	 */
	public IsbnJaCadastradoException(Throwable cause) {
		super(cause);
	}
	
}