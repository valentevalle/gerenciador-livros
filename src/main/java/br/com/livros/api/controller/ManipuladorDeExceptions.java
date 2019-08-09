package br.com.livros.api.controller;

import javax.validation.ValidationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.livros.dominio.exception.IsbnJaCadastradoException;
import br.com.livros.dominio.exception.LivroNaoEncontradoException;

/**
 * Manipulador de exceções.
 * 
 * @author Rafael
 */
@ControllerAdvice
public class ManipuladorDeExceptions extends ResponseEntityExceptionHandler {

	/**
	 * Método para tratar livros não existentes.
	 */
	@ExceptionHandler({ LivroNaoEncontradoException.class })
	protected ResponseEntity<Object> notFound(Exception e, WebRequest request) {
		return handleExceptionInternal(e, "Livro não encontrado.", new HttpHeaders(), 
				HttpStatus.NOT_FOUND, request);
	}

	/**
	 * Método para tratar livros já cadastrados.
	 */
	@ExceptionHandler({ IsbnJaCadastradoException.class })
	protected ResponseEntity<Object> badRequest(Exception e, WebRequest request) {
		return handleExceptionInternal(e, "Já há livro com esse ISBN já cadastrado.", new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * Método para tratar livros com atributos obrigatórios não preenchidos.
	 */
	@ExceptionHandler({ ValidationException.class })
	protected ResponseEntity<Object> unprocessableEntity(Exception e, WebRequest request) {
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), 
				HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

}